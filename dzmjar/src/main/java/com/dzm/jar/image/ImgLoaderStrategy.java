package com.dzm.jar.image;

/**
 * @author 邓治民
 *         data 2018/7/2 上午9:13
 */

public interface ImgLoaderStrategy {

    void loadImage(ImgLoaderOptions options);

    /**
     * 清理内存缓存
     */
    void clearMemoryCache();

    /**
     * 清理磁盘缓存
     */
    void clearDiskCache();

}
