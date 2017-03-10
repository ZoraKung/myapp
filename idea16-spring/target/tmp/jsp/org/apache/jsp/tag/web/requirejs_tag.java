package org.apache.jsp.tag.web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Arrays;
import java.util.List;

public final class requirejs_tag
    extends javax.servlet.jsp.tagext.SimpleTagSupport
    implements org.apache.jasper.runtime.JspSourceDependent {


  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(41);
    _jspx_dependants.add("/WEB-INF/common/taglibs.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery-ui-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/chosen-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/select2-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/datetimepicker-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jqgrid-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/ui.multiselect-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/fullcalendar-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/displaytag-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/ckeditor-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/dropzone-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery.multiselect-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/bootstrap-editable-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/ui.tabs.paging-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/uploadify-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/dynatree-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/uploadify-browse-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jstree-css.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery-ui-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/chosen-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/select2-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/datetimepicker-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jqgrid-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/ui.multiselect-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/fullcalendar-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/displaytag-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/typeahead-bs2-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery.populate-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery.validate-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/bootstrap-tag-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/dropzone-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery.multiselect-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/bootstrap-editable-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/ui.tabs.paging-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/uploadify-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery.form-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/dynatree-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jquery.maskedinput-js.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/upload.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/boactions.jsp");
    _jspx_dependants.add("/WEB-INF/common/include/jstree-js.jsp");
  }

  private JspContext jspContext;
  private java.io.Writer _jspx_sout;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public void setJspContext(JspContext ctx) {
    super.setJspContext(ctx);
    java.util.ArrayList<String> _jspx_nested = null;
    java.util.ArrayList<String> _jspx_at_begin = null;
    java.util.ArrayList<String> _jspx_at_end = null;
    this.jspContext = new org.apache.jasper.runtime.JspContextWrapper(ctx, _jspx_nested, _jspx_at_begin, _jspx_at_end, null);
  }

  public JspContext getJspContext() {
    return this.jspContext;
  }
  private java.lang.String id;

  public java.lang.String getId() {
    return this.id;
  }

  public void setId(java.lang.String id) {
    this.id = id;
  }

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  private void _jspInit(ServletConfig config) {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(config);
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
  }

  public void doTag() throws JspException, java.io.IOException {
    PageContext _jspx_page_context = (PageContext)jspContext;
    HttpServletRequest request = (HttpServletRequest) _jspx_page_context.getRequest();
    HttpServletResponse response = (HttpServletResponse) _jspx_page_context.getResponse();
    HttpSession session = _jspx_page_context.getSession();
    ServletContext application = _jspx_page_context.getServletContext();
    ServletConfig config = _jspx_page_context.getServletConfig();
    JspWriter out = jspContext.getOut();
    _jspInit(config);
    if( getId() != null ) {
      _jspx_page_context.setAttribute("id", getId());
}

    try {
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
      out.write("\r\n");

    String[] requires = id.split("[;,\\s]");
    List<String> requireList = Arrays.asList(requires);
    //request.setAttribute("requires", requires);
    //request.setAttribute("requires", requireList);

      out.write("\r\n");
      out.write("<content tag=\"header.requires\">\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jquery-ui")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/jquery-ui-1.10.3.custom.min.css\" rel=\"stylesheet\"/>\r\n");
      out.write('\r');
      out.write('\n');
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/jquery-ui-1.10.3.full.min.css\" rel=\"stylesheet\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("chosen")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/chosen.css\" rel=\"stylesheet\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("select2")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/select2.css\" rel=\"stylesheet\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("datetimepicker")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/datepicker.css\" rel=\"stylesheet\"/>\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/bootstrap-timepicker.css\" rel=\"stylesheet\"/>\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/bootstrap-datetimepicker.min.css\" rel=\"stylesheet\"/>\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/daterangepicker.css\" rel=\"stylesheet\" type=\"text/css\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jqgrid")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/ui.jqgrid.css\" rel=\"stylesheet\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("ui.multiselect")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/ui.multiselect.css\" rel=\"stylesheet\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("fullcalendar")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/fullcalendar.css\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("displaytag")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/displaytag/displaytag.css\"/>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("typeahead-bs2")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("ckeditor")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/ckeditor/ckeditor.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("bootstrap-tag")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("dropzone")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/dropzone.css\"/>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jquery.multiselect")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jqueryui/extends/multiselect/jquery.multiselect.css\" rel=\"stylesheet\"/>\r\n");
      out.write("<link type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jqueryui/extends/multiselect/jquery.multiselect.filter.css\" rel=\"stylesheet\"/>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("bootstrap.editable")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/css/bootstrap-editable.css\" type=\"text/css\"/>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("ui.tabs.paging")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jqueryui/extends/tabs/ui.tabs.paging.css\" rel=\"stylesheet\"/>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("uploadify")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/customize/uploadify/uploadify.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("dynatree")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/utils/messenger/css/dynatree/skin-vista/ui.dynatree.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("uploadify-browse")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/customize/uploadify/uploadify.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/customize/uploadify/uploadify-browse.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jstree")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/utils/messenger/css/jstree/jstree.min.css\">\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("</content>\r\n");
      out.write("\r\n");
      out.write("<content tag=\"footer.requires\">\r\n");
      out.write("    ");
 if (requireList.contains("jquery-ui")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jquery-ui-1.10.3.custom.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jquery-ui-1.10.3.full.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jquery.ui.touch-punch.min.js\" type=\"text/javascript\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("chosen")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/chosen.jquery.min.js\" type=\"text/javascript\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("select2")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/select2.min.js\" type=\"text/javascript\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("datetimepicker")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/date-time/moment.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/date-time/bootstrap-datepicker.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/date-time/bootstrap-timepicker.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/date-time/bootstrap-datetimepicker.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/date-time/daterangepicker.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jqgrid")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jqGrid/ui.multiselect.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jqGrid/jquery.jqGrid.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jqGrid/i18n/grid.locale-en.js\"></script>\r\n");
      out.write("\r\n");
      out.write('\r');
      out.write('\n');
      out.write("\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/scripts/jqgrid-common.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/scripts/jqgrid-nav.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/scripts/jqgrid-format.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/scripts/jqgrid-misc.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/scripts/jqgrid-frozen-fixed.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("ui.multiselect")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/ui.multiselect.js\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("fullcalendar")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/fullcalendar.min.js\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("displaytag")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("typeahead-bs2")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/typeahead-bs2.min.js\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("ckeditor")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/ckeditor/ckeditor.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jquery.populate")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/extends/jquery-populate/jquery.populate.js\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jquery.validate")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jquery.validate.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jquery.metadata.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/extends/jquery-validation/1.11.1/additional-methods.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/scripts/wjxinfo.validate.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/scripts/message_cn.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("bootstrap-tag")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/bootstrap-tag.min.js\" type=\"text/javascript\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("dropzone")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/dropzone.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jquery.multiselect")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jqueryui/extends/multiselect/jquery.multiselect.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("bootstrap.editable")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/x-editable/bootstrap-editable.min.js\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/x-editable/ace-editable.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("ui.tabs.paging")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jqueryui/extends/tabs/ui.tabs.paging.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/scripts/mis/tabs/tabs_extends.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("uploadify")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/customize/uploadify/jquery.uploadify.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jquery.form")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/extends/form/jquery.form.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("dynatree")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/utils/messenger/js/jquery.dynatree.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jquery.maskedinput")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jquery.maskedinput.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ace3}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/assets/js/jquery.numeric.js\" type=\"text/javascript\"></script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("uploadify-browse")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/jquery/customize/uploadify/jquery.uploadify.min.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("upload")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctxstatic}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/common/ajaxfileupload.js\" type=\"text/javascript\"></script>\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("boactions")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    var boactions = [];\r\n");
      out.write("    $(document).ready(function () {\r\n");
      out.write("\r\n");
      out.write("        $.ajax({\r\n");
      out.write("            type: \"POST\",\r\n");
      out.write("            url: _appContext + \"/misc/boactions\",\r\n");
      out.write("            dataType: \"json\",\r\n");
      out.write("            contentType: \"application/json\",\r\n");
      out.write("            async:false,\r\n");
      out.write("            success: function (data) {\r\n");
      out.write("                boactions = data;\r\n");
      out.write("                // var result = isActionEnabled(\"IND_MEM_APPLICATION\", \"DRAFT\", \"EDIT\");\r\n");
      out.write("                // alert(result);\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    function isActionEnabled(boType, boCurrentStatus, boAction) {\r\n");
      out.write("        var result = false;\r\n");
      out.write("        $.each(boactions, function (index, value) {\r\n");
      out.write("            var _boType = value.boType.toUpperCase();\r\n");
      out.write("            var _boStatus = value.boStatus.toUpperCase();\r\n");
      out.write("            var _actionEnabled = value.boActionEnabled.toUpperCase();\r\n");
      out.write("\r\n");
      out.write("            if (boType.toUpperCase() === _boType && boCurrentStatus.toUpperCase() === _boStatus) {\r\n");
      out.write("                if (_actionEnabled.indexOf(boAction.toUpperCase()) >= 0) {\r\n");
      out.write("                    result = true;\r\n");
      out.write("                    return false; // break out\r\n");
      out.write("                }\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("        return result;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("</script>");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("    ");
 if (requireList.contains("jstree")) { 
      out.write("\r\n");
      out.write("    ");
      out.write("\r\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)this.getJspContext(), null));
      out.write("/static/utils/messenger/js/jstree.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("    ");
}
      out.write("\r\n");
      out.write("</content>");
    } catch( Throwable t ) {
      if( t instanceof SkipPageException )
          throw (SkipPageException) t;
      if( t instanceof java.io.IOException )
          throw (java.io.IOException) t;
      if( t instanceof IllegalStateException )
          throw (IllegalStateException) t;
      if( t instanceof JspException )
          throw (JspException) t;
      throw new JspException(t);
    } finally {
      ((org.apache.jasper.runtime.JspContextWrapper) jspContext).syncEndTagFile();
      _jspDestroy();
    }
  }

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("ctx");
    _jspx_th_c_set_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)this.getJspContext(), null));
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      throw new SkipPageException();
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }

  private boolean _jspx_meth_c_set_1(PageContext _jspx_page_context)
          throws Throwable {
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_1.setPageContext(_jspx_page_context);
    _jspx_th_c_set_1.setParent(null);
    _jspx_th_c_set_1.setVar("ctxstatic");
    _jspx_th_c_set_1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}/static", java.lang.Object.class, (PageContext)this.getJspContext(), null));
    int _jspx_eval_c_set_1 = _jspx_th_c_set_1.doStartTag();
    if (_jspx_th_c_set_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_1);
      throw new SkipPageException();
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_1);
    return false;
  }

  private boolean _jspx_meth_c_set_2(PageContext _jspx_page_context)
          throws Throwable {
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
      throw new SkipPageException();
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_2);
    return false;
  }

  private boolean _jspx_meth_c_set_3(PageContext _jspx_page_context)
          throws Throwable {
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
      throw new SkipPageException();
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_3);
    return false;
  }

  private boolean _jspx_meth_c_set_4(PageContext _jspx_page_context)
          throws Throwable {
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
      throw new SkipPageException();
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_4);
    return false;
  }

  private boolean _jspx_meth_c_set_5(PageContext _jspx_page_context)
          throws Throwable {
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
      throw new SkipPageException();
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_5);
    return false;
  }

  private boolean _jspx_meth_c_set_6(PageContext _jspx_page_context)
          throws Throwable {
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
      throw new SkipPageException();
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_6);
    return false;
  }
}
