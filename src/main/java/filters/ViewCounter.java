/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import dao.JobDao;
import dao.ResumeDao;
import exceptions.DaoException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Admin
 */
public class ViewCounter implements Filter {

    private ScheduledExecutorService scheduler;
    private final Map<Integer, Integer> resumeViewCounts = new ConcurrentHashMap<>();
    private final Map<Integer, Integer> jobViewCounts = new ConcurrentHashMap<>();

    private final Runnable updateViews = () -> {
        if (!resumeViewCounts.isEmpty()) {
            ResumeDao resumeDao = new ResumeDao();
            Map<Integer, Integer> snapshot = getViewsSnapshot(resumeViewCounts);
            resumeViewCounts.clear();
            try {
                resumeDao.increaseResumeViewCounts(snapshot);
            } catch (DaoException ex) {
                Logger.getLogger(ViewCounter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!jobViewCounts.isEmpty()) {
            JobDao jobDao = new JobDao();
            Map<Integer, Integer> snapshot = getViewsSnapshot(jobViewCounts);
            jobViewCounts.clear();
            try {
                jobDao.increaseJobViewCounts(snapshot);
            } catch (DaoException ex) {
                Logger.getLogger(ViewCounter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(updateViews, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        processViews(request);
        chain.doFilter(request, response);
    }

    private void processViews(ServletRequest request) throws NumberFormatException {
        String resumeId = request.getParameter("resumeId");
        String jobId = request.getParameter("jobId");
        if (resumeId != null) {
            resumeViewCounts.compute(Integer.parseInt(resumeId), (k, v) -> v == null ? 1 : v + 1);
            System.out.println(resumeViewCounts.toString());
        }
        if (jobId != null) {
            jobViewCounts.compute(Integer.parseInt(jobId), (k, v) -> v == null ? 1 : v + 1);
            System.out.println(jobViewCounts.toString());
        }
    }

    @Override
    public void destroy() {
        scheduler.shutdownNow();
    }

    public Map<Integer, Integer> getViewsSnapshot(Map<Integer, Integer> originalMap) {
        Map<Integer, Integer> snapshot = new HashMap<>();
        snapshot.putAll(originalMap);
        return snapshot;
    }

}
