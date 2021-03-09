/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author Admin
 */
public class HelpCard extends SimpleTagSupport {
    
    private String question;
    private String answer;
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    @Override
    public void doTag() throws JspException, IOException {
        getJspContext().getOut().write("<details class=\"my-4 bg-white\">\n"
                + "    <summary class=\"p-2 border rounded\">\n"
                + "        <h6 style=\"display: inline\">" + question + "?</h6>\n"
                + "    </summary>\n"
                + "    <p>\n"
                + "        " + answer + ".\n"
                + "    </p>\n"
                + "</details>");
    }
    
}
