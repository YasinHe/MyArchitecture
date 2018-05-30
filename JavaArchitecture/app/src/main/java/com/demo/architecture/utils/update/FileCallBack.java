package com.demo.architecture.utils.update;

import com.demo.architecture.utils.L;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;

/**
 * Created by HeYingXin on 2018/3/22.
 */
public abstract class FileCallBack<T> {
    private String destFileDir;
    private String destFileName;
    private CompositeDisposable compositeDisposable;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        compositeDisposable = new CompositeDisposable();
    }

    public abstract void progress(long progress, long total);

    public void saveFile(ResponseBody body) {
        InputStream is = null;
        byte[] buf = new byte[4096];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                L.e("saveFile", e.getMessage());
            }
        }
    }
}

