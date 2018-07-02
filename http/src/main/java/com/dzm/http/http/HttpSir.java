package com.dzm.http.http;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.dzm.http.intercept.HttpInterceptInterface;
import com.dzm.http.server.HttpServer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 邓治民
 *         data 2018/3/8 上午11:19
 */

public class HttpSir {

    private static Build build = new Build();

    private static SparseArray<Http> sparseArray = new SparseArray<>();

    private static HttpServerImpl httpServer = new HttpServerImpl();

    public static HttpInterface execute(){
        if(null == httpServer){
            synchronized (HttpSir.class){
                if(null == httpServer){
                    httpServer = new HttpServerImpl();
                }
            }
        }
        return httpServer.http();
    }

    public static HttpInterface execute(Context context){
        if(null == httpServer){
            synchronized (HttpSir.class){
                if(null == httpServer){
                    httpServer = new HttpServerImpl();
                }
            }
        }
        return httpServer.http(context);
    }

    public static HttpInterface execute(Fragment fragment){
        if(null == httpServer){
            synchronized (HttpSir.class){
                if(null == httpServer){
                    httpServer = new HttpServerImpl();
                }
            }
        }
        return httpServer.http(fragment);
    }

    public static <T> T initService(Class<T> service, String url){
        if(null == httpServer){
            synchronized (HttpSir.class){
                if(null == httpServer){
                    httpServer = new HttpServerImpl();
                }
            }
        }
        return httpServer.initService(service,url);
    }

    public static <T> T initService(Class<T> service, String url, HttpServer.TimeBuild timeout){
        if(null == httpServer){
            synchronized (HttpSir.class){
                if(null == httpServer){
                    httpServer = new HttpServerImpl();
                }
            }
        }
        return httpServer.initService(service,url,timeout);
    }


    static HttpInterface initHttp(Context context){
        Http httpInterface = sparseArray.get(context.hashCode());
        if(null == httpInterface){
            httpInterface = new Http();
            httpInterface.init(context);
            sparseArray.append(context.hashCode(),httpInterface);
        }
        return httpInterface;
    }

    static HttpInterface initHttp(Fragment fragment){
        Http httpInterface = sparseArray.get(fragment.hashCode());
        if(null == httpInterface){
            httpInterface = new Http();
            httpInterface.init(fragment);
            sparseArray.append(fragment.hashCode(),httpInterface);
        }
        return httpInterface;
    }

    static HttpInterface initHttp(){
        return new Http();
    }

    static void removeHttp(int code){
        sparseArray.remove(code);
    }

    public static void onDestroy(int code){
        Http httpInterface = sparseArray.get(code);
        if(null != httpInterface){
            httpInterface.onDestroy();
        }
    }

    public static Build getBuild() {
        return build;
    }

    public static class Build{

        private Map<Class<? extends HttpInterceptInterface>,HttpInterceptInterface> interfaceMap = new HashMap<>();
        private HttpInterceptInterface defult;

        public Build addDefultIntercept(HttpInterceptInterface interceptInterface){
            this.defult = interceptInterface;
            interfaceMap.put(interceptInterface.getClass(),interceptInterface);
            return this;
        }

        public Build addIntercept(HttpInterceptInterface interceptInterface){
            interfaceMap.put(interceptInterface.getClass(),interceptInterface);
            return this;
        }

        HttpInterceptInterface getDefult() {
            return defult;
        }

        public Map<Class<? extends HttpInterceptInterface>, HttpInterceptInterface> getInterfaceMap() {
            return interfaceMap;
        }

        HttpInterceptInterface getIntercept(Class<? extends HttpInterceptInterface> clz) {
            return interfaceMap.get(clz);
        }

    }

}
