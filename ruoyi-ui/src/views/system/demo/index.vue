<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="条件" prop="field">
            <el-select v-model="queryParams.field1">
              <el-option
                v-for="item in fields"
                :key="item.fieldValue"
                :label="item.comment"
                :value="item.fieldValue">
                <span style="float: left">{{ item.comment }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.fieldValue }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="条件值" prop="value">
            <el-input
              v-model="value"
              placeholder="请输入条件值"
              clearable
              @input="valueValidate"
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <!--复合型输入框-->
        <!--<el-input
          v-model="value"
          placeholder="请输入条件值"
          clearable
          :blur="inputValidate"
          size="small"
          style="width: 430px;margin: 20px 5px"
          @keyup.enter.native="handleQuery">
          <el-select v-model="queryParams.field1" style="width: 80px" slot="prepend" placeholder="请选择">
            <el-option
              v-for="item in fields"
              :key="item.fieldValue"
              :label="item.comment"
              :value="item.fieldValue">
              <span style="float: left">{{ item.comment }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.fieldValue }}</span>
            </el-option>
          </el-select>
          <el-button slot="append" style="color:#409eff" icon="el-icon-search" @click="handleQuery"></el-button>
          <el-button slot="append" icon="el-icon-refresh-left" @click="resetQuery"></el-button>
        </el-input>-->
        <el-row>
          <el-col :span="12">
            <!--<el-table v-loading="loading" :data="demoList">
              <el-table-column label="编号" align="center" key="id" prop="id" v-if="columns[0].visible"/>
              <el-table-column label="省份" align="center" key="province" prop="province" v-if="columns[1].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="城市" align="center" key="city" prop="city" v-if="columns[2].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="数量" align="center" key="number" prop="number" v-if="columns[3].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="单位" align="center" key="company" prop="company" v-if="columns[4].visible"
                               width="100"/>
            </el-table>-->

            <!--动态添加列信息-->
            <el-table v-loading="loading" :data="demoList">
              <el-table-column v-for="col in fields" :label="col.comment" align="center" :key="col.fieldValue"
                               :prop="col.fieldValue"
                               :show-overflow-tooltip="true"/>
            </el-table>
            <pagination
              v-show="total>0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getList"
            />
          </el-col>
          <el-col :span="12">
            <template>
              <div style="padding-left:50px;margin-top: 10px">
                <label>科目</label>
                <el-select v-model="queryParams2.field" placeholder="请选择" @change="changeCharts">
                  <el-option
                    v-for="item in fields2"
                    :key="item.fieldValue"
                    :label="item.comment"
                    :value="item.fieldValue">
                    <span style="float: left">{{ item.comment }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.fieldValue }}</span>
                  </el-option>
                </el-select>
              </div>
            </template>
            <div id="myChart" :style="{width: '100%', height: '500px'}"></div>
          </el-col>
        </el-row>

      </el-col>
    </el-row>
    <el-dialog title="选择需要显示的数据" :visible.sync="open" width="600px" append-to-body>

    </el-dialog>
  </div>
</template>

<script>
  import {fields, listChart, listDemo} from "../../../api/system/demo";

  export default {
    name: "Demo",
    data() {
      return {
        // 遮罩层
        loading: true,
        //控制输入框是否只读
        read: false,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // Demo表格
        demoList: null,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        // table查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          field1: undefined,
        },
        // 图表分组统计参数
        queryParams2: {
          field: undefined,
        },
        //下拉框1与table列值
        fields: [],
        //下拉框2
        fields2: [],

        //设置下拉框默认值
        value: '',
        //图表数据
        fieldName: '',
        names: [],
        fieldValues: [],
      };
    },
    watch: {},

    created() {
      this.getFields();
    },
    methods: {
      //输入框校验
      valueValidate(e) {
        const numRegex = /^[0-9]*$/
        if (this.queryParams.field1 === 'id' || this.queryParams.field1 === 'number') {
          if (!numRegex.test(e)) {
            this.$message.warning("编号只能输入数字")
            this.value = ''
          }
        }
      },
      changeCharts() {
        this.getChart()
      },
      /** 根据fieldValue的值获取comment(下拉框label属性的值)*/
      chart(temp, value) {
        //获取select中的label得值
        let obj = {};
        obj = temp.find((item) => { //遍历list的数据
          return item.fieldValue === value; //筛选出匹配数据
        });
        this.fieldName = obj.comment;
      },
      /** 获取数据库字段名称和注释值*/
      getFields() {
        fields().then(response => {
          response.data.forEach((val, index) => {
            this.fields.push(val)
          })
          response.fields2.forEach((val, index) => {
            this.fields2.push(val)
          })
          //设置默认值为第一个
          this.queryParams.field1 = this.fields[0].fieldValue;
          this.queryParams2.field = this.fields2[0].fieldValue;
          this.getList();

        });
      },
      /** 查询Demo列表*/
      getList() {
        const fieldVal1 = this.queryParams.field1;
        //动态添加对象属性
        this.queryParams[fieldVal1] = this.value;
        this.loading = true;
        listDemo(this.queryParams).then(response => {

            this.demoList = response.rows;
            this.total = response.total;
            //动态删除对象属性:放在获取图表数据之前，防止图表数据属性变多
            this.$delete(this.queryParams, fieldVal1)
            this.getChart();
            this.loading = false;
          },
        );
      },
      /** Demo图表数据*/
      getChart() {
        this.fieldName = ''
        this.fieldValues = []
        this.names = []
        const fieldVal2 = this.queryParams.field1;
        //动态添加对象属性
        this.queryParams2[fieldVal2] = this.value;
        listChart(this.queryParams2).then(response => {
          response.chart.forEach((value, index) => {
            this.names.push(value.fieldValue)
            this.fieldValues.push(value.sum)
            this.chart(this.fields2, this.queryParams2.field)
            //动态删除对象属性
            this.$delete(this.queryParams2, fieldVal2)
          })
          this.drawLine();
        })
      },
      /** 图表*/
      drawLine() {
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          //工具栏
          toolbox: {
            feature: {
              //保存图表图片功能
              saveAsImage: {}
            }
          },
          xAxis: {
            data: this.names,
            //数据显示在刻度上（false）
            boundaryGap: true,
            axisLabel: {
              //解决图标变窄时数据显示不全问题
              interval: 0,
            },
          },
          yAxis: {},
          legend: {
            //数据必须以数组的形式表示
            data: [this.fieldName],
            show: true,
            bottom: 0,
            left: 'center',
            textStyle: {
              color: "#0b3bd2",
              fontSize: 14
            },
          },
          series: [{
            name: this.fieldName,
            type: 'line',
            data: this.fieldValues,
            //显示最大值与最小值
            markPoint: {
              data: [
                {type: 'min', name: '最小值'},
                {type: 'max', name: '最大值'},
              ]
            },
            //显示平均值
            markLine: {
              data: [
                {type: 'average', name: '平均值'}
              ]
            },
            itemStyle: {
              normal: {
                color: "#0b3bd2"
              }
            }
          }]
        });
        /** 设置图标自适应大小*/
        setTimeout(function () {
          window.onresize = function () {
            myChart.resize()
          }
        }, 100)
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.value = '';
        this.fields = [];
        this.fields2 = [];
        this.getFields();
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
    }
  }
</script>

<style scoped>

</style>
