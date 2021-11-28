package cn.enncy.mall.service;


import cn.enncy.mall.mapper.AddressMapper;
import cn.enncy.mall.pojo.Address;

import java.util.List;

/**
 * //TODO
 * <br/>Created in 17:18 2021/11/18
 *
 * @author enncy
 */
public class AddressService extends BaseService<Address, AddressMapper> implements  AddressMapper{

    public AddressService() {
        super(AddressMapper.class);
    }


    @Override
    public Address findOneByAlias(String alias) {
        return mapper.findOneByAlias(alias);
    }

    @Override
    public List<Address> findByUserId(long userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public List<Address> search(String str, int page, int size) {
        size = size == 0 ? 10 : size;
        return mapper.search(str,page*size, size);
    }


}
