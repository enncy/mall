package cn.enncy.mall.controller.restful;


import cn.enncy.mall.bean.ResultBody;
import cn.enncy.mall.constant.OrderStatus;
import cn.enncy.mall.pojo.Order;

import cn.enncy.mall.service.OrderService;
import cn.enncy.mall.service.TagService;

import cn.enncy.mybatis.core.ServiceFactory;
import cn.enncy.spring.mvc.annotation.Get;
import cn.enncy.spring.mvc.annotation.RestController;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.io.IOException;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * //TODO
 * <br/>Created in 0:05 2021/12/3
 *
 * @author enncy
 */

@RestController
public class ChartRestController {

    OrderService orderService = ServiceFactory.resolve(OrderService.class);
    TagService tagService = ServiceFactory.resolve(TagService.class);

    @Get("/chart")
    public ResultBody chart() throws IOException {
        setTheme();
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        List<Map<String, Object>> saleInfos = orderService.analysisGoodsSaleOfDay(day - 1);

        for (Map<String, Object> saleInfo : saleInfos) {

            dataSet.addValue(Integer.valueOf(saleInfo.get("nums").toString()), saleInfo.get("tag").toString(), saleInfo.get("day").toString());
        }
        //
        JFreeChart chart = ChartFactory.createBarChart(
                "???????????????????????????????????????",
                "??????",
                "??????",
                dataSet,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        chart.setBackgroundPaint(null);
        Plot plot = chart.getPlot();
        plot.setBackgroundAlpha(0);
        plot.setBackgroundImage(null);
        plot.setBackgroundPaint(null);
        plot.setOutlinePaint(null);
        plot.setForegroundAlpha(0.6f);

        return ResultBody.of(createChartBase64(chart));
    }


    @Get("/pie")
    public ResultBody pie() throws IOException {
        setTheme();
        //?????????????????????
        //????????????????????????????????????
        //?????????????????????????????????????????????OK???
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        List<Order> orders = orderService.findAll();
        long paymentCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.PAYMENT.value)).count();
        long receivingCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.RECEIVING.value)).count();
        long finishCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.FINISH.value)).count();
        long returnCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.RETURN.value)).count();
        long cancelCount = orders.stream().map(Order::getStatus).filter(s -> s.equals(OrderStatus.CANCEL.value)).count();

        dataset.setValue("?????????", paymentCount);
        dataset.setValue("?????????", receivingCount);
        dataset.setValue("?????????", finishCount);
        dataset.setValue("?????????", returnCount);
        dataset.setValue("??????", cancelCount);

        JFreeChart chart = ChartFactory.createPieChart3D("?????????????????????", dataset, true, true, false);

        PiePlot pieplot = (PiePlot) chart.getPlot();
        chart.setBackgroundPaint(null);
        pieplot.setBackgroundAlpha(0);
        pieplot.setBackgroundPaint(null);
        pieplot.setOutlinePaint(null);
        PieSectionLabelGenerator pie = new StandardPieSectionLabelGenerator("{0}:({1}.{2})");
        pieplot.setLegendLabelGenerator(pie);
        pieplot.setForegroundAlpha(0.6f);

        return ResultBody.of(createChartBase64(chart));
    }

    public String createChartBase64(JFreeChart chart) throws IOException {
        return createChartBase64(chart, 600, 400);
    }

    public String createChartBase64(JFreeChart chart, int w, int h) throws IOException {
        String encode = Base64.encodeBase64String(ChartUtils.encodeAsPNG(chart.createBufferedImage(w,h)));
        return "data:image/png;base64," + encode;
    }


    public void setTheme(){
        //??????????????????
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");

        //??????????????????
        standardChartTheme.setExtraLargeFont(new Font("??????",Font.BOLD,20));
        //?????????????????????
        standardChartTheme.setRegularFont(new Font("??????",Font.BOLD,15));
        //?????????????????????
        standardChartTheme.setLargeFont(new Font("??????",Font.BOLD,15));
        //??????????????????
        ChartFactory.setChartTheme(standardChartTheme);

    }
}
