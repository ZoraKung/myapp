
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="businessAddressValue" type="org.hkicpa.mas.common.vo.AddressVo" required="false" description="Business Address of Value" %>
<%@ attribute name="residentialAddressValue" type="org.hkicpa.mas.common.vo.AddressVo" required="false" description="Residential Address of Value" %>
<%@ attribute name="businessAddressName" type="java.lang.String" required="true" description="Business Address of Name" %>
<%@ attribute name="residentialAddressName" type="java.lang.String" required="true" description="ResidentialAddress Address of Name"%>
<%@ attribute name="addressType" type="java.lang.String" required="false" description="Address Type" %>
<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>

<c:if test="${leftCols == null}"><c:set var="leftCols" value="col-sm-3" /></c:if>
<c:if test="${rightCols == null}"><c:set var="rightCols" value="col-sm-9" /></c:if>

<div class="row">
    <div class="form-group">
        <label class="${leftCols} control-label"></label>
        <div class="${rightCols} controls">
            <label>
                <input type="radio" class="ace required" name="modelAddress" value="local_en" checked="checked"/>
                <span class="lbl">local</span>
            </label>&nbsp&nbsp&nbsp
            <label>
                <input type="radio" class="ace required" name="modelAddress" value="local_zh"  />
                <span class="lbl">本地</span>
            </label>&nbsp&nbsp&nbsp
            <label>
                <input type="radio" class="ace required" name="modelAddress" value="china_zh" />
                <span class="lbl"> 中国 </span>
            </label>&nbsp&nbsp&nbsp
            <label>
                <input type="radio" class="ace required" name="modelAddress" value="overseas_zh"/>
                <span class="lbl">海外</span>
            </label>&nbsp&nbsp&nbsp
            <label>
                <input type="radio" class="ace required" name="modelAddress" value="overseas_en"/>
                <span class="lbl"> Oversea </span>
            </label>
        </div>
    </div>
</div>
<div class="space-10"></div>

<div id="${id}_address_div">
  <div id="residentialAddress_div">
    <span class="label label-primary arrowed-in-right label-lg">
        <b>Residential address</b>
    </span>
     <div class="space-10"></div>
    <div class="form-group col-xs-12">
        <label class="col-xs-3 control-label" id="residential_country_lbl">Country</label>
        <div class="col-xs-9">
            <input class="input-large" type="text" name="${residentialAddressName}.countryUid" id="residential_country" readonly="true" />
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="col-sm-3 control-label" id="residential_attentionTo_lbl">AttentionTo</label>
        <div class="col-sm-9">
            <input class="input-large"  type="text" name="${residentialAddressName}.attentionTo" id="residential_attentionTo" value="${residentialAddressValue.attentionTo}" />
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="col-sm-3 control-label">Address</label>
        <div class="col-sm-9">
                Flat/Room<input style="width:40px" type="text" name="" id="residential_flat" minlength="1" maxlength="2" class="input-sm text" /> &nbsp;
                Floor<input style="width:40px" type="text" name="" id="residential_floor" minlength="1" maxlength="2" class="input-sm text" /> &nbsp;
                Block<input style="width:40px" type="text" name="" id="residential_block" minlength="1" maxlength="2" class="input-sm text" /> &nbsp;
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="col-sm-3 control-label"></label>
        <div class="col-sm-9">
            <input class="input-xlarge" type="text" name="${residentialAddressName}.field2Address" id="residential_field2Address" value="${residentialAddressValue.field2Address}"/>
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="col-sm-3 control-label"></label>
        <div class="col-sm-9">
            <input class="input-xlarge" type="text" name="${residentialAddressName}.field3Address" id="residential_field3Address" value="${residentialAddressValue.field3Address}"/>
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="col-sm-3 control-label"></label>
        <div class="col-sm-9">
            <select class="select2 input-xlarge " name="${residentialAddressName}.field4Address" id="residential_field4Address"></select>
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="col-sm-3 control-label"></label>
        <div class="col-sm-9">
            <select class="select2 input-xlarge " name="${residentialAddressName}.field5Address" id="residential_field5Address"></select>
        </div>
    </div>
 </div>
 <div class="space-10"></div>
  <div class="business_div">
    <span class="label label-primary arrowed-in-right label-lg">
        <b>Business address</b>
    </span>
     <div class="space-10"></div>
      <div class="form-group col-xs-12">
          <label class="col-xs-3 control-label" id="business_country_lbl">Country</label>
          <div class="col-xs-9">
              <input class="input-large" type="text" name="${businessAddressName}.countryUid" id="business_country" readonly="true" />
          </div>
      </div>
      <div class="form-group col-xs-12">
          <label class="col-sm-3 control-label" id="business_attentionTo_lbl">AttentionTo</label>
          <div class="col-sm-9">
              <input class="input-large" type="text" name="${businessAddressName}.attentionTo" id="business_attentionTo" value="${businessAddressValue.attentionTo}" />
          </div>
      </div>
      <div class="form-group col-xs-12">
          <label class="col-sm-3 control-label" id="">Company Name</label>
          <div class="col-sm-9">
              <input class="input-large" type="text" name="${businessAddressName}.companyName" id="business_companyName" value="${businessAddressValue.companyName}" />
          </div>
      </div>
      <div class="form-group col-xs-12">
          <label class="col-sm-3">Address</label>
          <div class="col-sm-9">
                  Flat/Room<input style="width:40px" type="text" name="" id="business_flat" minlength="1" maxlength="2" class="input-sm text" /> &nbsp;
                  Floor<input style="width:40px" type="text" name="" id="business_floor" minlength="1" maxlength="2" class="input-sm text" /> &nbsp;
                  Block<input style="width:40px" type="text" name="" id="business_block" minlength="1" maxlength="2" class="input-sm text" /> &nbsp;
          </div>
      </div>
      <div class="form-group col-xs-12">
          <label class="col-sm-3 control-label"></label>
          <div class="col-sm-9">
              <input class="input-xlarge" type="text" name="${businessAddressName}.field2Address" id="business_field2Address" value="${businessAddressValue.field2Address}"/>
          </div>
      </div>
      <div class="form-group col-xs-12">
          <label class="col-sm-3 control-label"></label>
          <div class="col-sm-9">
              <input class="input-xlarge" type="text" name="${businessAddressName}.field3Address" id="business_field3Address" value="${businessAddressValue.field3Address}"/>
          </div>
      </div>
      <div class="form-group col-xs-12">
          <label class="col-sm-3 control-label"></label>
          <div class="col-sm-9">
              <select class="select2 input-xlarge " name="${businessAddressName}.field4Address" id="business_field4Address"></select>
          </div>
      </div>
      <div class="form-group col-xs-12">
          <label class="col-sm-3 control-label"></label>
          <div class="col-sm-9">
              <select class="select2 input-xlarge " name="${businessAddressName}.field5Address" id="business_field5Address"></select>
          </div>
      </div>
  </div>
</div>

<script type="text/javascript">
    $(function(){
        $("#residential_field4Address").dropdownList('district', '${residentialAddressValue.field5Address}');
        $("#residential_field5Address").dropdownList('sub_district', '${residentialAddressValue.field4Address}');
        $("#business_field4Address").dropdownList('district', '${businessAddressValue.field5Address}');
        $("#business_field5Address").dropdownList('sub_district', '${businessAddressValue.field4Address}');

        $("input[name='modelAddress']").change( function() {

            var selectRadioValue = $("input[name='modelAddress']:checked").val();
            var residential_attentionTo = $("#residential_attentionTo").val();
            var residential_flat = $("#residential_flat").val();
            var residential_floor = $("#residential_floor").val();
            $.ajax({
                type: "POST",
                url: "${ctx}/common/address/ajax/refresh-address",
                data:{
                    'radioNum':selectRadioValue,
                    'residential_attentionTo':residential_attentionTo,
                    'residential_flat':residential_flat,
                    'residential_floor':residential_floor,
                    'residential_block':$("#residential_block").val,
                    'residential_field2Address':$("#residential_field2Address").val,
                    'residential_field3Address':$("#residential_field3Address").val,
                    'residential_field4Address':$("#residential_field4Address").val,
                    'residential_field5Address':$("#residential_field5Address").val,

                    'business_attentionTo':$("#business_attentionTo").val,
                    'business_flat':$("#business_flat").val,
                    'business_floor':$("#business_floor").val,
                    'business_block':$("#business_block").val,
                    'business_field2Address':$("#business_field2Address").val,
                    'business_field3Address':$("#business_field3Address").val,
                    'business_field4Address':$("#business_field4Address").val,
                    'business_field5Address':$("#business_field5Address").val
                },
                success:function(response){
                    $("#${id}_address_div").html(response);
                }
            })
        });
    })
</script>





