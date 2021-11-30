package cn.enncy.mall.service.goods;


import cn.enncy.mall.pojo.Goods;
import cn.enncy.mall.pojo.Tag;
import cn.enncy.mall.service.GoodsService;
import cn.enncy.mall.service.TagService;
import cn.enncy.mall.service.impl.GoodsServiceImpl;
import cn.enncy.mybatis.core.ServiceFactory;

/**
 * 商品标签业务
 * <br/>Created in 14:09 2021/11/27
 *
 * @author enncy
 */
public class GoodsTagService  extends GoodsServiceImpl {


    TagService tagService = ServiceFactory.resolve(TagService.class);

    @Override
    public boolean insert(Goods baseObject) {
        if (baseObject.getTagId() != 0) {
            Tag tag = tagService.findOneById(baseObject.getTagId());
            tag.setCount(tag.getCount() + 1);
            tagService.update(tag);
        }
        return super.insert(baseObject);
    }

    @Override
    public boolean update(Goods baseObject) {
        Goods old = mapper.findOneById(baseObject.getId());

        // 如果未存在标签
        if (old.getTagId() == 0) {
            // 并且更改的标签不为0
            if (baseObject.getTagId() != 0) {
                Tag tag = tagService.findOneById(baseObject.getTagId());
                tag.setCount(tag.getCount() + 1);
                tagService.update(tag);
            }
        } else if (old.getTagId() != 0) {
            // 并且更改的标签不为0 以及与旧标签不一致
            if (baseObject.getTagId() != 0 && old.getTagId() != baseObject.getTagId()) {
                // 更新新标签引用
                Tag tag = tagService.findOneById(baseObject.getTagId());
                tag.setCount(tag.getCount() + 1);
                tagService.update(tag);
                // 减少旧标签引用次数
                Tag oldTag = tagService.findOneById(old.getTagId());
                oldTag.setCount(Math.max(oldTag.getCount() - 1,0));
                tagService.update(oldTag);
            }
        }

        return super.update(baseObject);
    }

    @Override
    public boolean delete(Goods baseObject) {
        if (baseObject.getTagId() != 0) {
            Tag tag = tagService.findOneById(baseObject.getTagId());
            tag.setCount(tag.getCount() - 1);
            tagService.update(tag);
        }
        return super.delete(baseObject);
    }
}
