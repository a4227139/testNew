
package com.thread.executor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
public class DisplayProductInfoWithExecutorService {

    //�̳߳�
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    //���ڸ�ʽ��
    private final DateFormat format = new SimpleDateFormat("HH:mm:ss");

    // ģ�������վ��Ʒ�������Ϣչʾ
    // ���ڿ�����Ʒ��ͼƬ���ܻ��кܶ��ţ�������ʾ��Ʒ��ͼƬ��������һ�����ӳ�
    // ������Ʒ�������⻹������Ʒ������Ϣ��չʾ������������Ϣ��Ҫ��������Ϊ
    // ���������ܹ���ͼƬ������ʾ����������Ĵ������ִ������������Ϊ���ߣ���
    // �������������ִ�С����������������ִ�д��ڽϴ��࣬�����뵽�ĵ�һ��
    // ˼·�����첽ִ�У�����ִ��ͼ�����������֮�󣨲���ܾã���ʼִ����Ʒ
    // �����Ϣ��չʾ����������㹻�ã�ͼƬ�ֲ��Ǻܴ������£������ڿ�ʼչʾ
    // ��Ʒ��ʱ��ͼ�����������ˣ�������Ȼ�뵽ʹ��Executor��Callable�����
    // �������ִ�С�

    public void renderProductDetail() {
        final List<ProductInfo>  productInfos = loadProductImages();

        //�첽����ͼ�������
        Callable<List<ProductImage>> task = new Callable<List<ProductImage>>() {

            @Override
            public List<ProductImage> call() throws Exception {
                List<ProductImage> imageList = new ArrayList<>();
                for (ProductInfo info : productInfos){
                    imageList.add(info.getImage());
                }
                return imageList;
            }
        };

        //�ύ���̳߳�ִ��
        Future<List<ProductImage>> listFuture = executorService.submit(task);
        //չʾ��Ʒ������Ϣ
        renderProductText(productInfos);

        try {
            //��ʾ��Ʒ��ͼƬ
            List<ProductImage> imageList = listFuture.get();
            renderProductImage(imageList);
        } catch (InterruptedException e) {
            // �����ʾͼƬ�����ж��쳣�����������̵߳��ж�״̬
            // ������������wait�е��̻߳���
            Thread.currentThread().interrupt();
            // ͬʱȡ�������ִ��,����false��ʾ���߳���ִ�в��ж�
            listFuture.cancel(true);
        } catch (ExecutionException e) {
            try {
                throw new Throwable(e.getCause());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }

    private void renderProductImage(List<ProductImage> imageList ) {
        for (ProductImage image : imageList){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " display products images! "
            + format.format(new Date()));
    }

    private void renderProductText(List<ProductInfo> productInfos) {
        for (ProductInfo info : productInfos){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " display products description! "
            + format.format(new Date()));
    }

    private List<ProductInfo> loadProductImages() {
        List<ProductInfo> list = new ArrayList<>();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProductInfo info = new ProductInfo();
        info.setImage(new ProductImage());
        list.add(info);
        System.out.println(Thread.currentThread().getName() + " load products info! "
                + format.format(new Date()));
        return list;
    }

    /**
     * ��Ʒ
     */
    private static class ProductInfo{
        private ProductImage image;

        public ProductImage getImage() {
            return image;
        }

        public void setImage(ProductImage image) {
            this.image = image;
        }
    }

    private static class ProductImage{}

    public static void main(String[] args){
        DisplayProductInfoWithExecutorService cd = new DisplayProductInfoWithExecutorService();
        cd.renderProductDetail();
        System.exit(0);
    }
}