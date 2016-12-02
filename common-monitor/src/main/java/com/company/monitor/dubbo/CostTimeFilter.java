package com.company.monitor.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.company.monitor.Constant;
import com.company.monitor.TraceKeyHolder;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>耗时过滤器</p>
 *
 * @author wangzhj
 * @time 2016-11-30 18:55
 */
@Activate(group = {Constants.CONSUMER, Constants.PROVIDER})
public class CostTimeFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CostTimeFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        logger.info("CostTimeFilter......");
        long start = System.currentTimeMillis();
        //接口全限定名
        String canonicalName = invoker.getInterface().getCanonicalName();
        String methodName = invocation.getMethodName();
        String fqName = Joiner.on(".").join(canonicalName, methodName);
        //上下文
        RpcContext context = RpcContext.getContext();
        logger.info("context.isConsumerSide():"+context.isConsumerSide());
    /*    if(context.isConsumerSide()){
            String traceKey = TraceKeyHolder.getTraceKey();
            logger.info("traceKey"+traceKey);
            context.setAttachment(Constant.CALL_TRACE_KEY, traceKey);
        } else {
            String callTrackId = context.getAttachment(Constant.CALL_TRACE_KEY);
            logger.info("callTrackId="+callTrackId);
        }*/
        String callTraceKey = context.getAttachment(Constant.CALL_TRACE_KEY);
        logger.info("callTraceKey="+callTraceKey);
        //
        Result result = null;
        try {
            result = invoker.invoke(invocation);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Dubbo Interface[{}] [COST TIME] [{}] s", fqName, (end - start) / 1000.00D);
        }
        return result;
    }
}
