package cn.enncy.mall.service.impl;


import cn.enncy.mall.mapper.AddressMapper;
import cn.enncy.mall.pojo.Address;
import cn.enncy.mall.service.AddressService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * //TODO
 * <br/>Created in 17:58 2021/11/30
 *
 * @author enncy
 */
public class AddressServiceImpl  extends ServiceImpl<Address,AddressMapper> implements AddressService {

    public AddressServiceImpl() {
        super(AddressMapper.class);
    }


    @Override
    public Address findOneByUserAlias(long userId,String alias) {
        return mapper.findOneByUserAlias(userId,alias);
    }

    @Override
    public List<Address> findByUserId(long userId) {
        return mapper.findByUserId(userId);
    }

    @Override
    public List<Address>  searchByUserId(long userId, String str, int page, int size){
        return search(str, page, size).stream().filter(address -> address.getUserId() == userId).collect(Collectors.toList());
    }
    @Override
    public List<Address> search(String str, int page, int size) {
        size = size == 0 ? 10 : size;
        return mapper.search(str,page*size, size);
    }
}
