<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <!--<el-col :span="4" :xs="24">
          <div class="head-container"><el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>-->
      <!--用户数据-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="学生学号" prop="id">
            <el-input
              v-model="queryParams.id"
              placeholder="请输入学生学号"
              clearable
              size="small"
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="学生姓名" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入学生姓名"
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

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:grade:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:grade:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:grade:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:grade:import']"
            >导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:grade:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="gradeList" @selection-change="handleSelectionChange"
                  show-summary
                  :summary-method="getSumGrades">
          <el-table-column type="selection" v-if="checkRole(['admin'])" width="50" align="center" />
          <el-table-column label="学生学号" align="center" key="id" prop="id" v-if="columns[0].visible" />
          <el-table-column label="学生姓名" align="center" key="name" prop="name" v-if="columns[1].visible"
                           :show-overflow-tooltip="true" />
          <el-table-column label="语文成绩" align="center" key="chinese" prop="chinese" v-if="columns[2].visible"
                           :show-overflow-tooltip="true" />
          <el-table-column label="英语成绩" align="center" key="english" prop="english" v-if="columns[3].visible"
                           :show-overflow-tooltip="true" />
          <el-table-column label="数学成绩" align="center" key="mathematics" prop="mathematics" v-if="columns[4].visible"
                           width="100" />
          <el-table-column label="历史成绩" align="center" key="history" prop="history" v-if="columns[5].visible"
                           width="100" />
          <el-table-column label="生物成绩" align="center" key="biology" prop="biology" v-if="columns[6].visible"
                           width="100" />
          <el-table-column label="化学成绩" align="center" key="chemistry" prop="chemistry" v-if="columns[7].visible"
                           width="100" />
          <el-table-column label="物理成绩" align="center" key="physics" prop="physics" v-if="columns[8].visible"
                           width="100" />
          <el-table-column label="地理成绩" align="center" key="geography" prop="geography" v-if="columns[9].visible"
                           width="100" />
          <el-table-column label="政治成绩" align="center" key="politics" prop="politics" v-if="columns[10].visible"
                           width="100" />
          <!--:formatter="formatAvg"-->
          <el-table-column label="平均成绩" align="center" key="avg" prop="avg"
                           v-if="columns[11].visible"
                           width="100" />
          <el-table-column label="状态" align="center" key="status" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="学生姓名" prop="name">
              <el-input v-model="form.name" :readonly="read" placeholder="请输入学生姓名" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="语文成绩" prop="chinese">
              <el-input v-model="form.chinese" placeholder="请输入语文成绩" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="英语成绩" prop="english">
              <el-input v-model="form.english" placeholder="请输入学生姓名" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数学成绩" prop="mathematics">
              <el-input v-model="form.mathematics" placeholder="请输入数学成绩" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="历史成绩" prop="history">
              <el-input v-model="form.history" placeholder="请输入历史成绩" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生物成绩" prop="biology">
              <el-input v-model="form.biology" placeholder="请输入生物成绩" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="化学成绩" prop="chemistry">
              <el-input v-model="form.chemistry" placeholder="请输入化学成绩" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物理成绩" prop="physics">
              <el-input v-model="form.physics" placeholder="请输入物理成绩" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="地理成绩" prop="geography">
              <el-input v-model="form.geography" placeholder="请输入地理成绩" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="政治成绩" prop="politics">
              <el-input v-model="form.politics" placeholder="请输入政治成绩" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="text-align: center">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  //权限控制函数
  import { checkPermi,checkRole } from "@/utils/permission";
  import {listGrade,updateGrade,addGrade,getGrade,deleteGrade} from "../../../api/system/grade";
  import {getToken} from "../../../utils/auth";

  export default {
    name: "Grade",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        //控制输入框是否只读
        read:false,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 学生成绩表格
        gradeList: null,
        // 弹出层标题
        title: "",
        // 部门树选项
        deptOptions: undefined,
        // 是否显示弹出层
        open: false,
        // 部门名称
        deptName: undefined,
        // 默认密码
        initPassword: undefined,
        // 日期范围
        dateRange: [],
        // 岗位选项
        postOptions: [],
        // 角色选项
        roleOptions: [],
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: "",
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: { Authorization: "Bearer " + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/system/grade/importData"
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          id: undefined,
          name: undefined,
        },
        // 列信息
        columns: [
          { key: 0, label: `学生学号`, visible: true },
          { key: 1, label: `学生姓名`, visible: true },
          { key: 2, label: `语文成绩`, visible: true },
          { key: 3, label: `英语成绩`, visible: true },
          { key: 4, label: `数学成绩`, visible: true },
          { key: 5, label: `历史成绩`, visible: true },
          { key: 6, label: `生物成绩`, visible: true },
          { key: 7, label: `化学成绩`, visible: true },
          { key: 8, label: `物理成绩`, visible: true },
          { key: 9, label: `地理成绩`, visible: true },
          { key: 10, label: `政治成绩`, visible: true },
          { key: 11, label: `平均成绩`, visible: true }
        ],
        // 表单校验
        rules: {
          name: [
            { required: true, message: "学生姓名不能为空", trigger: "blur" },
          ],
          chinese: [
            { required: true, message: "语文成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          english: [
            { required: true, message: "英语成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          mathematics: [
            { required: true, message: "数学成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          history: [
            { required: true, message: "历史成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          biology: [
            { required: true, message: "生物成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          chemistry: [
            { required: true, message: "化学成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          physics: [
            { required: true, message: "物理成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          geography: [
            { required: true, message: "地理成绩不能为空", trigger: "blur" },
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
          politics: [
            { required: true, message: "政治成绩不能为空", trigger: "blur" },
            /*{
              pattern: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
              message: "请输入正确的邮件格式",
              trigger: "blur",
            },*/
            { pattern: /^((\d|[1-9]\d|1[0-4]\d)((\.5)?)|150)$/, message: "请输入正确的成绩格式", trigger: "blur" },
          ],
        }
      };
    },
    watch: {
      form(val){
        console.log(val)
      }
    },
    created() {
      this.getList();
    },
    methods: {
      //权限控制
      checkRole,
      checkPermi,
      /** 查询用户列表 */
      getList() {
        this.loading = true;
        listGrade(this.queryParams).then(response => {
            this.gradeList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      /** 格式化平均数*/
      /*formatAvg(row,column){
        // console.log(row)
        return row.avg.toFixed(2)
      },*/
      /** 自定义合计方法*/
      getSumGrades(param){
        const {columns,data}=param;
        const sums=[];
        // console.log(columns.length)
        columns.forEach((column,index)=>{
          if (index===0||index===1||index===columns.length-1||index===columns.length-2){
            sums[index]='';
            return;
          }
          if(index===2){
            sums[index]='总计';
            return;
          }
          //data后加 ||[]  主要是为了解决.map不能映射null
          const values=(data||[]).map(item=>Number(item[column.property]));
          if(!values.every(value=>isNaN(value))){
            sums[index]=values.reduce((prev,curr)=>{
              const value=Number(curr);
              if(!isNaN(value)){
                return prev+curr;
              }else{
                return prev;
              }
            },0);
            sums[index] +=' 分';
          }else{
            sums[index]='';
          }
        });
        return sums;
      },
      // 用户状态修改
      handleStatusChange(row) {
        console.log(row)
        let text = row.status === "0" ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(function() {
          // return changeUserStatus(row.userId, row.status);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          name: undefined,
          chinese: undefined,
          english: undefined,
          mathematics: undefined,
          history: undefined,
          biology: undefined,
          chemistry: undefined,
          physics: undefined,
          geography: undefined,
          politics: undefined,
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        /*this.getTreeselect();*/
        this.read=false;
        this.open = true;
        this.title = "添加学生成绩";

      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids;
        getGrade(id).then(response => {
          console.log(response.data)
          this.form = response.data;
          this.read=true;
          this.open = true;
          this.title = "修改学生成绩";
        });
      },
      /** 提交按钮 */
      submitForm: function() {
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
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const delIds = row.id || this.ids;
        this.$modal.confirm('是否确认删除学号为"' + delIds + '"的学生成绩？').then(function() {
          return deleteGrade(delIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/grade/export', {
          ...this.queryParams
        }, `grade_${new Date().getTime()}.xlsx`)
      },
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "成绩导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        this.download('system/grade/importTemplate', {
        }, `grade_template_${new Date().getTime()}.xlsx`)
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      }
    }
  };
</script>
