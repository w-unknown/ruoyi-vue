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

        <el-row>
          <el-col :span="12">
            <el-table v-loading="loading" :data="demoList">
              <el-table-column label="编号" align="center" key="id" prop="id" v-if="columns[0].visible"/>
              <el-table-column label="省份" align="center" key="province" prop="province" v-if="columns[1].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="城市" align="center" key="city" prop="city" v-if="columns[2].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="数量" align="center" key="number" prop="number" v-if="columns[3].visible"
                               :show-overflow-tooltip="true"/>
              <el-table-column label="单位" align="center" key="company" prop="company" v-if="columns[4].visible"
                               width="100"/>
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
              <div style="padding-left:150px;margin-top: 20px">
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
            <div id="myChart" :style="{width: '100%', height: '400px'}"></div>
          </el-col>
        </el-row>

      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {addGrade, deleteGrade, getGrade, listGrade, updateGrade} from "../../../api/system/grade";
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
        // 列信息
        columns: [
          {key: 0, label: `编号`, visible: true},
          {key: 1, label: `省份`, visible: true},
          {key: 2, label: `城市`, visible: true},
          {key: 3, label: `数量`, visible: true},
          {key: 4, label: `单位`, visible: true},
        ],
        //下拉框值
        fields: [],
        fields2: [],

        //设置下拉框默认值
        value: '',
        //图表数据
        fieldName: '',
        names: [],
        fieldValues: [],
      };
    },
    watch: {

    },

    created() {
      this.getFields();
    },
    methods: {
      //权限控制
      /*checkRole,
      checkPermi,*/
      changeCharts() {
        this.getChart()
      },
      //根据field的值获取label
      chart(value){
        //获取select中的label得值
        let obj = {};
        obj = this.project.find((item)=>{//遍历list的数据
          return item.value === this.value;//筛选出匹配数据
        });
        console.log(obj.label);
        this.fieldName=obj.label;
      },
      getFields() {
        fields().then(response => {
          console.log(response)
          response.data.forEach((val, index) => {
            this.fields.push(val)
          })
          response.fields2.forEach((val, index) => {
            this.fields2.push(val)
          })
          console.log(this.queryParams2)
          console.log(this.queryParams)
          //设置默认值为第一个
          this.queryParams.field1 = this.fields[0].fieldValue;
          this.queryParams2.field = this.fields2[0].fieldValue;
          // console.log(this.fields)
          // console.log(this.fields2)
          this.getList();
        });
      },
      // 查询Demo列表
      getList() {
        console.log(this.queryParams.field1)
        //动态添加对象属性
        this.queryParams[this.queryParams.field1] = this.value;
        console.log(this.queryParams)
        this.loading = true;
        listDemo(this.queryParams).then(response => {
            // console.log(this.queryParams)
            // console.log(response.rows)
            this.demoList = response.rows;
            // console.log(response.rows)
            this.total = response.total;
            /*console.log(this.fields2.indexOf(this.queryParams.field1))
            if(this.fields2.indexOf(this.queryParams.field1)>=0){
              this.getChart();
            }*/
            this.getChart();
            this.loading = false;
            //动态删除对象属性
            this.$delete(this.queryParams, this.queryParams.field1)
          },
        );
      },
      //Demo图表数据
      getChart() {
        this.fieldName = ''
        this.fieldValues = []
        this.names = []
        console.log(this.queryParams2.field)
        //动态添加对象属性
        this.queryParams2[this.queryParams.field1] = this.value;
        listChart(this.queryParams2).then(response => {
          console.log(response)
          response.chart.forEach((value, index) => {
            this.names.push(value.fieldValue)
            this.fieldValues.push(value.sum)
            this.fieldName = this.queryParams2.field
            //动态删除对象属性
            this.$delete(this.queryParams2, this.queryParams.field1)
          })
          this.drawLine();
        })
      },
      //图表
      drawLine() {
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
          tooltip: {},
          xAxis: {
            data: this.names,
            axisLabel: {
              //解决图标变窄时数据显示不全问题
              interval: 0,
            },
          },
          yAxis: {},
          legend: {
            //数据为数组
            data: this.fieldName,
            show: true,
            bottom: 0,
            left: 'center',
            textStyle: {
              color: "#1fc3ce",
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
        //设置图标自适应大小
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
      /** 查询部门下拉树结构 */
      /*getTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        });
      },*/
      // 筛选节点
      /*filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },*/
      // 节点单击事件
      /*handleNodeClick(data) {
        this.queryParams.deptId = data.id;
        this.handleQuery();
      },*/
      // 用户状态修改
      /*handleStatusChange(row) {
        console.log(row)
        let text = row.status === "0" ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(function () {
          // return changeUserStatus(row.userId, row.status);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
      },*/
      // 取消按钮
      /*cancel() {
        this.open = false;
        this.reset();
      },*/
      // 表单重置
      /*reset() {
        this.form = {
          id: undefined,
          province: undefined,
          city: undefined,
          number: undefined,
          company: undefined,
        };
        this.resetForm("form");
      },*/
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },

      // 多选框选中数据
      /*handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },*/
      // 更多操作触发
      /*handleCommand(command, row) {
        switch (command) {
          case "handleResetPwd":
            this.handleResetPwd(row);
            break;
          case "handleAuthRole":
            this.handleAuthRole(row);
            break;
          default:
            break;
        }
      },*/
      /** 新增按钮操作 */
      /*handleAdd() {
        this.reset();
        /!*this.getTreeselect();*!/
        this.read = false;
        this.open = true;
        this.title = "添加学生成绩";
        /!*getUser().then(response => {
          /!*this.postOptions = response.posts;
          this.roleOptions = response.roles;*!/

          /!*this.form.password = this.initPassword;*!/
        });*!/
      },*/
      /** 修改按钮操作 */
      /*handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids;
        getGrade(id).then(response => {
          console.log(response.data)
          this.form = response.data;
          this.read = true;
          this.open = true;
          this.title = "修改学生成绩";
        });
      },*/
      /** 重置密码按钮操作 */
      /*handleResetPwd(row) {
        this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          closeOnClickModal: false,
          inputPattern: /^.{5,20}$/,
          inputErrorMessage: "用户密码长度必须介于 5 和 20 之间"
        }).then(({ value }) => {
          resetUserPwd(row.userId, value).then(response => {
            this.$modal.msgSuccess("修改成功，新密码是：" + value);
          });
        }).catch(() => {});
      },*/
      /** 分配角色操作 */
      /*handleAuthRole: function(row) {
        const userId = row.userId;
        this.$router.push("/system/user-auth/role/" + userId);
      },*/
      /** 提交按钮 */
      /*submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              updateGrade(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addGrade(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },*/
      /** 删除按钮操作 */
      /*handleDelete(row) {
        const delIds = row.id || this.ids;
        this.$modal.confirm('是否确认删除学号为"' + delIds + '"的学生成绩？').then(function () {
          return deleteGrade(delIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {
        });
      },*/
      /** 导出按钮操作 */
      /*handleExport() {
        this.download('system/grade/export', {
          ...this.queryParams
        }, `grade_${new Date().getTime()}.xlsx`)
      },*/
      /** 导入按钮操作 */
      /*handleImport() {
        this.upload.title = "成绩导入";
        this.upload.open = true;
      },*/
      /** 下载模板操作 */
      /*importTemplate() {
        this.download('system/grade/importTemplate', {}, `grade_template_${new Date().getTime()}.xlsx`)
      },*/
      // 文件上传中处理
      /*handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },*/
      // 文件上传成功处理
      /*handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
        this.getList();
      },*/
      // 提交上传文件
      /*submitFileForm() {
        this.$refs.upload.submit();
      }*/
    }
  }
</script>

<style scoped>

</style>
