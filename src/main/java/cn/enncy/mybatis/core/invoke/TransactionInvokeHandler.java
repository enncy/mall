package cn.enncy.mybatis.core.invoke;


import cn.enncy.mall.utils.Logger;
import cn.enncy.mybatis.annotation.method.Transaction;
import cn.enncy.mybatis.core.DBUtils;

import cn.enncy.mybatis.entity.MybatisException;


import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 事务处理器
 * <br/>Created in 15:45 2021/11/30
 *
 * @author enncy
 */
public class TransactionInvokeHandler  extends SqlInvokeHandler {

    Object target;

    public TransactionInvokeHandler(Object target) {
        super(target.getClass());
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws MybatisException {

        if(method.isAnnotationPresent(Transaction.class)){
            String savePoint = UUID.randomUUID().toString().split("-")[0];
            Logger.log(" ============ 事务开启"+savePoint+" ============");
            try {
                // 保存任务点
                DBUtils.connect(statement -> statement.execute("begin;"));
                DBUtils.connect(statement -> statement.execute("savepoint "+savePoint+";"));
                Object invoke = super.invoke(target, method, args);
                DBUtils.connect(statement -> statement.execute("commit;"));
                return invoke;
            }catch (MybatisException error){
                error.printStackTrace();
                Logger.log("============ 事务回滚"+savePoint+" ============");
                // 回滚
                DBUtils.connect(statement -> statement.execute("rollback to "+savePoint+";"));
            }
        }else{
            return  super.invoke(target, method, args);
        }
        return null;
    }

}
