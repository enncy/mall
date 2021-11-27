package cn.enncy.mall.controller;


import cn.enncy.mall.bean.Pagination;
import cn.enncy.mall.pojo.Cart;
import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.pojo.User;
import cn.enncy.mall.service.CartService;
import cn.enncy.mall.service.GoodsService;
import cn.enncy.mall.utils.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Controller;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.Post;
import cn.enncy.spring.mvc.annotation.params.Param;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * //TODO
 * <br/>Created in 16:13 2021/11/27
 *
 * @author enncy
 */


@Controller
public class GoodsController {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;

    GoodsService goodsService = ServiceFactory.resolve(GoodsService.class);
    CartService cartService = ServiceFactory.resolve(CartService.class);

    @Get("/goods")
    public String goods(@Param("page") int page, @Param("size") int size, @Param("search") String search, @Param("tag") String tag,@Param("order") String order) {
        size = size == 0 ? 10 : size;

        List<Goods> goodsList;
        Pagination pagination;
        if (StringUtils.isNullOrEmpty(tag)) {
            List<Goods> searchAll = goodsService.searchAll(search);
            pagination = Pagination.createPagination(page, size, searchAll.size());
            goodsList = searchAll.stream().skip((long) page * size).limit(size).collect(Collectors.toList());
        } else {
            pagination = Pagination.createPagination(page, size, goodsService.countByTagName(tag));
            // 按照标签以及页码进行查询，并且筛选出符合 search 的商品
            if(StringUtils.isNullOrEmpty(search)){
                goodsList = goodsService.findByTagName(tag, page, size);
            }else{
                goodsList = goodsService.findByTagName(tag, page, size).stream().filter(goods -> goods.getName().contains(search)).collect(Collectors.toList());
            }
        }

        // 排序
        if(!StringUtils.isNullOrEmpty(order)){
            if("desc".equals(order)){
                goodsList = goodsList.stream().sorted(Comparator.comparing(Goods::getRealPrice)).collect(Collectors.toList());
            }else{
                goodsList = goodsList.stream().sorted(Comparator.comparing(Goods::getRealPrice).reversed()).collect(Collectors.toList());
            }
        }

        // 分页
        request.setAttribute("pagination", pagination);
        request.setAttribute("goods", goodsList);

        return "/goods/index";
    }


    @Get("/goods/detail")
    public String detail(@Param("id") int id){
        Goods goods = goodsService.findOneById(id);
        request.setAttribute("goods", goods);
        return "/goods/detail/index";
    }

    @Get("/goods/buy")
    public String buy(@Param("id") int id){
        Goods goods = goodsService.findOneById(id);
        request.setAttribute("goods", goods);
        return "/goods/buy/index";
    }

    @Get("/goods/buy")
    public String postBuy(@Param("cartId") int cartId,@Param("id") int id,@Param("count") int count){
        //Goods goods;
        //// 直接购买
        //if(cartId==0){
        //    Cart cart = cartService.findOneById(cartId);
        //    long goodsId = cart.getGoodsId();
        //    goods =  goodsService.findOneById(goodsId);
        //    cartService.delete(cart);
        //}
        //// 购物车结算
        //else{
        //    goods = goodsService.findOneById(id);
        //}
        //
        //request.setAttribute("goods", goods);
        return "/goods/buy/index";
    }

    @Post("/goods/add")
    public void add(@Param("id") int id,@Param("count") int count) throws IOException, ServletException {
        User user = (User) session.getAttribute("user");
        if(user==null){
            response.sendRedirect("/login");
        }else{
            Goods goods = goodsService.findOneById(id);
            // 库存不够
            if(goods.getStock()==0 || count > goods.getStock()){
                request.setAttribute("error","商品库存不足！");
                request.setAttribute("id", goods.getId());
                request.setAttribute("goods",goods);
                request.getRequestDispatcher("/goods/detail/index.jsp").forward(request,response);
            }else{
                // 减少库存
                goods.setStock(goods.getStock()-count);
                goodsService.update(goods);
                // 添加购物车
                Cart cart = new Cart();
                cart.setGoodsId(goods.getId());
                cart.setCount(count);
                cart.setUserId(user.getId());
                cartService.insert(cart);

                response.sendRedirect("/user/cart?id="+cart.getId());
            }


        }

    }
}
