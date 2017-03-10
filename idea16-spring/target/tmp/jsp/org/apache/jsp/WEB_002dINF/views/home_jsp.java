package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class home_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(8);
    _jspx_dependants.add("/WEB-INF/common/taglibs.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/css/test-nav-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/js/test-nav-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery-min-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/js/test-clock-js.jsp");
    _jspx_dependants.add("/WEB-INF/views/idea16/test/goosamer.jsp");
    _jspx_dependants.add("/WEB-INF/views/idea16/test/_action.jsp");
    _jspx_dependants.add("/WEB-INF/tags/requirejs.tag");
  }

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_set_1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_set_2(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_set_3(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_set_4(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_set_5(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_set_6(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    ");
      if (_jspx_meth_tags_requirejs_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("    <title>首页</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("'");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${i}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("'\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div style=\"float:right;\" id=\"hub_iframe\"></div>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    function async_load() {\r\n");
      out.write("\r\n");
      out.write("        i.scrolling = \"no\";\r\n");
      out.write("        i.frameborder = \"0\";\r\n");
      out.write("        i.border = \"0\";\r\n");
      out.write("        i.setAttribute(\"frameborder\", \"0\", 0);\r\n");
      out.write("        i.width = \"100px\";\r\n");
      out.write("        i.height = \"20px\";\r\n");
      out.write("        document.getElementById(\"hub_iframe\").appendChild(i);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    if (window.addEventListener) {window.addEventListener(\"load\", async_load, false);}\r\n");
      out.write("    else if (window.attachEvent) {window.attachEvent(\"onload\", async_load);}\r\n");
      out.write("    else {window.onload = async_load;}\r\n");
      out.write("</script>\r\n");
      out.write("<script>\r\n");
      out.write("    ! function() {\r\n");
      out.write("        //封装方法，压缩之后减少文件大小\r\n");
      out.write("        function get_attribute(node, attr, default_value) {\r\n");
      out.write("            return node.getAttribute(attr) || default_value;\r\n");
      out.write("        }\r\n");
      out.write("        //封装方法，压缩之后减少文件大小\r\n");
      out.write("        function get_by_tagname(name) {\r\n");
      out.write("            return document.getElementsByTagName(name);\r\n");
      out.write("        }\r\n");
      out.write("        //获取配置参数\r\n");
      out.write("        function get_config_option() {\r\n");
      out.write("            var scripts = get_by_tagname(\"script\"),\r\n");
      out.write("                script_len = scripts.length,\r\n");
      out.write("                script = scripts[script_len - 1]; //当前加载的script\r\n");
      out.write("            return {\r\n");
      out.write("                l: script_len, //长度，用于生成id用\r\n");
      out.write("                z: get_attribute(script, \"zIndex\", -1), //z-index\r\n");
      out.write("                o: get_attribute(script, \"opacity\", 0.5), //opacity\r\n");
      out.write("                c: get_attribute(script, \"color\", \"0,0,0\"), //color\r\n");
      out.write("                n: get_attribute(script, \"count\", 99) //count\r\n");
      out.write("            };\r\n");
      out.write("        }\r\n");
      out.write("        //设置canvas的高宽\r\n");
      out.write("        function set_canvas_size() {\r\n");
      out.write("            canvas_width = the_canvas.width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,\r\n");
      out.write("                canvas_height = the_canvas.height = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        //绘制过程\r\n");
      out.write("        function draw_canvas() {\r\n");
      out.write("            context.clearRect(0, 0, canvas_width, canvas_height);\r\n");
      out.write("            //随机的线条和当前位置联合数组\r\n");
      out.write("            var e, i, d, x_dist, y_dist, dist; //临时节点\r\n");
      out.write("            //遍历处理每一个点\r\n");
      out.write("            random_lines.forEach(function(r, idx) {\r\n");
      out.write("                r.x += r.xa,\r\n");
      out.write("                    r.y += r.ya, //移动\r\n");
      out.write("                    r.xa *= r.x > canvas_width || r.x < 0 ? -1 : 1,\r\n");
      out.write("                    r.ya *= r.y > canvas_height || r.y < 0 ? -1 : 1, //碰到边界，反向反弹\r\n");
      out.write("                    context.fillRect(r.x - 0.5, r.y - 0.5, 1, 1); //绘制一个宽高为1的点\r\n");
      out.write("                //从下一个点开始\r\n");
      out.write("                for (i = idx + 1; i < all_array.length; i++) {\r\n");
      out.write("                    e = all_array[i];\r\n");
      out.write("                    //不是当前点\r\n");
      out.write("                    if (null !== e.x && null !== e.y) {\r\n");
      out.write("                        x_dist = r.x - e.x, //x轴距离 l\r\n");
      out.write("                            y_dist = r.y - e.y, //y轴距离 n\r\n");
      out.write("                            dist = x_dist * x_dist + y_dist * y_dist; //总距离, m\r\n");
      out.write("                        dist < e.max && (e === current_point && dist >= e.max / 2 && (r.x -= 0.03 * x_dist, r.y -= 0.03 * y_dist), //靠近的时候加速\r\n");
      out.write("                            d = (e.max - dist) / e.max,\r\n");
      out.write("                            context.beginPath(),\r\n");
      out.write("                            context.lineWidth = d / 2,\r\n");
      out.write("                            context.strokeStyle = \"rgba(\" + config.c + \",\" + (d + 0.2) + \")\",\r\n");
      out.write("                            context.moveTo(r.x, r.y),\r\n");
      out.write("                            context.lineTo(e.x, e.y),\r\n");
      out.write("                            context.stroke());\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("            }), frame_func(draw_canvas);\r\n");
      out.write("        }\r\n");
      out.write("        //创建画布，并添加到body中\r\n");
      out.write("        var the_canvas = document.createElement(\"canvas\"), //画布\r\n");
      out.write("            config = get_config_option(), //配置\r\n");
      out.write("            canvas_id = \"c_n\" + config.l, //canvas id\r\n");
      out.write("            context = the_canvas.getContext(\"2d\"), canvas_width, canvas_height,\r\n");
      out.write("            frame_func = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(func) {\r\n");
      out.write("                    window.setTimeout(func, 1000 / 45);\r\n");
      out.write("                }, random = Math.random,\r\n");
      out.write("            current_point = {\r\n");
      out.write("                x: null, //当前鼠标x\r\n");
      out.write("                y: null, //当前鼠标y\r\n");
      out.write("                max: 20000\r\n");
      out.write("            },\r\n");
      out.write("            all_array;\r\n");
      out.write("        the_canvas.id = canvas_id;\r\n");
      out.write("        the_canvas.style.cssText = \"position:fixed;top:0;left:0;z-index:\" + config.z + \";opacity:\" + config.o;\r\n");
      out.write("        get_by_tagname(\"body\")[0].appendChild(the_canvas);\r\n");
      out.write("        //初始化画布大小\r\n");
      out.write("\r\n");
      out.write("        set_canvas_size(), window.onresize = set_canvas_size;\r\n");
      out.write("        //当时鼠标位置存储，离开的时候，释放当前位置信息\r\n");
      out.write("        window.onmousemove = function(e) {\r\n");
      out.write("            e = e || window.event, current_point.x = e.clientX, current_point.y = e.clientY;\r\n");
      out.write("        }, window.onmouseout = function() {\r\n");
      out.write("            current_point.x = null, current_point.y = null;\r\n");
      out.write("        };\r\n");
      out.write("        //随机生成config.n条线位置信息\r\n");
      out.write("        for (var random_lines = [], i = 0; config.n > i; i++) {\r\n");
      out.write("            var x = random() * canvas_width, //随机位置\r\n");
      out.write("                y = random() * canvas_height,\r\n");
      out.write("                xa = 2 * random() - 1, //随机运动方向\r\n");
      out.write("                ya = 2 * random() - 1;\r\n");
      out.write("            random_lines.push({\r\n");
      out.write("                x: x,\r\n");
      out.write("                y: y,\r\n");
      out.write("                xa: xa,\r\n");
      out.write("                ya: ya,\r\n");
      out.write("                max: 6000 //沾附距离\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("        all_array = random_lines.concat([current_point]);\r\n");
      out.write("        //0.1秒后绘制\r\n");
      out.write("        setTimeout(function() {\r\n");
      out.write("            draw_canvas();\r\n");
      out.write("        }, 100);\r\n");
      out.write("    }();</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    $(function () {\r\n");
      out.write("        //toplink start\r\n");
      out.write("        $(\"a.topLink\").click(function () {\r\n");
      out.write("            $(\"html, body\").animate({\r\n");
      out.write("                    scrollTop: $($(this).attr(\"href\")).offset().top + \"px\"\r\n");
      out.write("                },\r\n");
      out.write("                {\r\n");
      out.write("                    duration: 500,\r\n");
      out.write("                    easing: \"swing\"\r\n");
      out.write("                });\r\n");
      out.write("            return false;\r\n");
      out.write("        });\r\n");
      out.write("        //toplink end\r\n");
      out.write("\r\n");
      out.write("        //nav\r\n");
      out.write("        showNav(arr);\r\n");
      out.write("\r\n");
      out.write("        //clock\r\n");
      out.write("        aa();\r\n");
      out.write("    });\r\n");
      out.write("    var arr = [\r\n");
      out.write("        {\r\n");
      out.write("            id: 1,\r\n");
      out.write("            level: 1,\r\n");
      out.write("            name: 'a1',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: ''\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 2,\r\n");
      out.write("            level: 2,\r\n");
      out.write("            name: 'a2',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 1\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 22,\r\n");
      out.write("            level: 2,\r\n");
      out.write("            name: 'a22',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 1\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 222,\r\n");
      out.write("            level: 2,\r\n");
      out.write("            name: 'a222',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 1\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 3,\r\n");
      out.write("            level: 3,\r\n");
      out.write("            name: 'a3',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 2\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 33,\r\n");
      out.write("            level: 3,\r\n");
      out.write("            name: 'a33',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 22\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 333,\r\n");
      out.write("            level: 3,\r\n");
      out.write("            name: 'a333',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 222\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 4,\r\n");
      out.write("            level: 1,\r\n");
      out.write("            name: 'b1',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: ''\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 5,\r\n");
      out.write("            level: 2,\r\n");
      out.write("            name: 'b2',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 4\r\n");
      out.write("        },\r\n");
      out.write("        {\r\n");
      out.write("            id: 6,\r\n");
      out.write("            level: 3,\r\n");
      out.write("            name: 'b3',\r\n");
      out.write("            href: 'http://baidu.com',\r\n");
      out.write("            parId: 5\r\n");
      out.write("        }\r\n");
      out.write("    ];\r\n");
      out.write("\r\n");
      out.write("</script>\r\n");
      out.write('\r');
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("ctx");
    _jspx_th_c_set_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }

  private boolean _jspx_meth_c_set_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_1.setPageContext(_jspx_page_context);
    _jspx_th_c_set_1.setParent(null);
    _jspx_th_c_set_1.setVar("ctxstatic");
    _jspx_th_c_set_1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}/static", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_c_set_1 = _jspx_th_c_set_1.doStartTag();
    if (_jspx_th_c_set_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_1);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_1);
    return false;
  }

  private boolean _jspx_meth_c_set_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_2 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_2.setPageContext(_jspx_page_context);
    _jspx_th_c_set_2.setParent(null);
    _jspx_th_c_set_2.setVar("dateFormat");
    _jspx_th_c_set_2.setValue(new String("dd-MM-yyyy"));
    int _jspx_eval_c_set_2 = _jspx_th_c_set_2.doStartTag();
    if (_jspx_th_c_set_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_2);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_2);
    return false;
  }

  private boolean _jspx_meth_c_set_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_3 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_3.setPageContext(_jspx_page_context);
    _jspx_th_c_set_3.setParent(null);
    _jspx_th_c_set_3.setVar("dateTimeFormat");
    _jspx_th_c_set_3.setValue(new String("dd-MM-yyyy HH:mm"));
    int _jspx_eval_c_set_3 = _jspx_th_c_set_3.doStartTag();
    if (_jspx_th_c_set_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_3);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_3);
    return false;
  }

  private boolean _jspx_meth_c_set_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_4 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_4.setPageContext(_jspx_page_context);
    _jspx_th_c_set_4.setParent(null);
    _jspx_th_c_set_4.setVar("timeFormat");
    _jspx_th_c_set_4.setValue(new String("HH:mm"));
    int _jspx_eval_c_set_4 = _jspx_th_c_set_4.doStartTag();
    if (_jspx_th_c_set_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_4);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_4);
    return false;
  }

  private boolean _jspx_meth_c_set_5(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_5 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_5.setPageContext(_jspx_page_context);
    _jspx_th_c_set_5.setParent(null);
    _jspx_th_c_set_5.setVar("moneyFormat");
    _jspx_th_c_set_5.setValue(new String("HK$0.00"));
    int _jspx_eval_c_set_5 = _jspx_th_c_set_5.doStartTag();
    if (_jspx_th_c_set_5.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_5);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_5);
    return false;
  }

  private boolean _jspx_meth_c_set_6(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_6 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_6.setPageContext(_jspx_page_context);
    _jspx_th_c_set_6.setParent(null);
    _jspx_th_c_set_6.setVar("percentFormat");
    _jspx_th_c_set_6.setValue(new String("0.00%"));
    int _jspx_eval_c_set_6 = _jspx_th_c_set_6.doStartTag();
    if (_jspx_th_c_set_6.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_6);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_6);
    return false;
  }

  private boolean _jspx_meth_tags_requirejs_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tags:requirejs
    org.apache.jsp.tag.web.requirejs_tag _jspx_th_tags_requirejs_0 = (_jspx_resourceInjector != null) ? _jspx_resourceInjector.createTagHandlerInstance(org.apache.jsp.tag.web.requirejs_tag.class) : new org.apache.jsp.tag.web.requirejs_tag();
    _jspx_th_tags_requirejs_0.setJspContext(_jspx_page_context);
    _jspx_th_tags_requirejs_0.setId("jquery,nav,clock");
    _jspx_th_tags_requirejs_0.doTag();
    return false;
  }
}
