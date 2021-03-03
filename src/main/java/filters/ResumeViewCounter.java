/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

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
public class ResumeViewCounter implements Filter {

    private ScheduledExecutorService scheduler;
    private final Map<Integer, Integer> viewCounts = new ConcurrentHashMap<>();

    private final Runnable updateViews = () -> {
        if (viewCounts.isEmpty()) {
            return;
        }
        ResumeDao resumeDao = new ResumeDao();
        Map<Integer, Integer> snapshot = getViewsSnapshot();
        viewCounts.clear();
        try {
            resumeDao.increaseResumeCounts(snapshot);
        } catch (DaoException ex) {
            Logger.getLogger(ResumeViewCounter.class.getName()).log(Level.SEVERE, null, ex);
        }
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleWithFixedDelay(updateViews, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String resumeId = request.getParameter("resumeId");
        if (resumeId != null) {
            viewCounts.compute(Integer.parseInt(resumeId), (k, v) -> v == null ? 1 : v + 1);
            System.out.println(viewCounts.toString());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        scheduler.shutdownNow();
    }

    public Map<Integer, Integer> getViewsSnapshot() {
        Map<Integer, Integer> snapshot = new HashMap<>();
        snapshot.putAll(viewCounts);
        return snapshot;
    }

}
