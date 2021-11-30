package cn.enncy.mall.service;


import cn.enncy.mall.mapper.AddressMapper;
import cn.enncy.mall.pojo.Address;
import cn.enncy.mall.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 17:18 2021/11/18
 *
 * @author enncy
 */
public interface AddressService extends BaseService<Address> {


    Address findOneByAlias(String alias);

    List<Address> findByUserId(long userId);

    List<Address> searchByUserId(long userId, String str, int page, int size);

    List<Address> search(String str, int page, int size);

}
