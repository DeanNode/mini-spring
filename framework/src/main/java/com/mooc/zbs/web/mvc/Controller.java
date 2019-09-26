package com.mooc.zbs.web.mvc;

import java.lang.annotation.*;

@Documented
//保留到运行期
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller  {

}
