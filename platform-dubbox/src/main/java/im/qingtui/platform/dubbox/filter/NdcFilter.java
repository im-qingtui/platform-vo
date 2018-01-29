package im.qingtui.platform.dubbox.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import java.util.UUID;
import org.apache.log4j.NDC;

/**
 * NDC 事务日志跟踪
 *
 * @author bowen
 */
@Activate(group = {Constants.PROVIDER})
public class NdcFilter implements Filter {

    public Result invoke(Invoker<?> invoker, Invocation invocation) {

        Result result;
        try {
            String address = RpcContext.getContext().getRemoteAddressString();
            String loggerName = new StringBuilder(invoker.getInterface().getSimpleName()).append(".").append
                (invocation.getMethodName()).toString();
            NDC.push(new StringBuffer(loggerName).append("-").append(address).append("-").append(UUID.randomUUID().toString().split("-")[0]).toString());
            result = invoker.invoke(invocation);
        } finally {
            NDC.pop();
        }
        return result;

    }
}
