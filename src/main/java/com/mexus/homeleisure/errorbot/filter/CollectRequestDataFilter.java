package com.mexus.homeleisure.errorbot.filter;

import com.mexus.homeleisure.errorbot.util.AgentUtils;
import com.mexus.homeleisure.errorbot.util.MDCUtil;
import com.mexus.homeleisure.errorbot.util.RequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Request 정보들을 수집하여 MDC에 보관하는 필터
 *
 * @author always0ne
 * @version 1.0
 */
public class CollectRequestDataFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper = RequestWrapper.of(request);

        MDCUtil.setJsonValueAndPutMDC(MDCUtil.HEADER_MAP_MDC, requestWrapper.headerMap());
        MDCUtil.setJsonValueAndPutMDC(MDCUtil.PARAMETER_MAP_MDC, requestWrapper.parameterMap());
        MDCUtil.setJsonValueAndPutMDC(MDCUtil.BODY_MDC, requestWrapper.body());
        MDCUtil.setJsonValueAndPutMDC(MDCUtil.AGENT_DETAIL_MDC, AgentUtils.getAgentDetail((HttpServletRequest) request));
        MDCUtil.putMDC(MDCUtil.REQUEST_URI_MDC, requestWrapper.getRequestUri());

        try {
            chain.doFilter(request, response);
        } finally {
            MDCUtil.clear();
        }
    }
}