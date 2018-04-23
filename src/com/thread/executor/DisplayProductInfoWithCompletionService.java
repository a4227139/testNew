
package com.thread.executor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class DisplayProductInfoWithCompletionService {

    //�̳߳�
    private final ExecutorService executorService;
    //���ڸ�ʽ��
    private final DateFormat format = new SimpleDateFormat("HH:mm:ss");

    public DisplayProductInfoWithCompletionService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public void renderProductDetail() {

        final List<ProductInfo> productInfos = loadProductInfos();

        CompletionService<ProductImage> completionService = new ExecutorCompletionService<ProductImage>(executorService);

        //Ϊÿ��ͼ������ؽ���һ����������
        for (final ProductInfo info : productInfos) {
            completionService.submit(new Callable<ProductImage>() {
                @Override
                public ProductImage call() throws Exception {
                    return info.getImage();
                }
            });
        }

        //չʾ��Ʒ������Ϣ
        renderProductText(productInfos);

        try {
            //��ʾ��ƷͼƬ
            for (int i = 0, n = productInfos.size(); i < n; i++){
                Future<ProductImage> imageFuture = completionService.take();
                ProductImage image = imageFuture.get();
                renderProductImage(image);
            }
        } catch (InterruptedException e) {
            // �����ʾͼƬ�����ж��쳣�����������̵߳��ж�״̬
            // ������������wait�е��̻߳���
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            try {
                throw new Throwable(e.getCause());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }


    private void renderProductImage(ProductImage image) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " display products images! "
                + format.format(new Date()));
    }

    private void renderProductText(List<ProductInfo> productInfos) {
        for (ProductInfo info : productInfos) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " display products description! "
                + format.format(new Date()));
    }

    private List<ProductInfo> loadProductInfos() {
        List<ProductInfo> list = new ArrayList<>();
        try {
            TimeUnit.SECONDS.sleep(3);
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
    private static class ProductInfo {
        private ProductImage image;

        public ProductImage getImage() {
            return image;
        }

        public void setImage(ProductImage image) {
            this.image = image;
        }
    }

    private static class ProductImage {
    }

    public static void main(String[] args) {
        DisplayProductInfoWithCompletionService cd = new DisplayProductInfoWithCompletionService(Executors.newCachedThreadPool());
        cd.renderProductDetail();
    }
}