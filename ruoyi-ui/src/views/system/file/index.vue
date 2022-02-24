<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <!--<div class="head-container"><el-input
          v-model="deptName"
          placeholder="请输入部门名称"
          clearable
          size="small"
          prefix-icon="el-icon-search"
          style="margin-bottom: 20px"
        />
      </div>-->
        <!--<div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>-->
      </el-col>
      <!--用户数据-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
          <el-form-item label="" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入学生学号"
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
        <!--<el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:grade:add']"
            >新增</el-button>
          </el-col>-->
        <!--<el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:grade:edit']"
          >修改</el-button>
        </el-col>-->
        <!--<el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:grade:remove']"
          >删除</el-button>
        </el-col>-->
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['system:file:upload']"
          >上传
          </el-button>
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleDownload"
            v-hasPermi="['system:file:download']"
          >下载</el-button>
          <el-button
            type="primary"
            plain
            size="mini"
            @click="handProtfolio"
            v-hasPermi="['system:file:add']"
          >新建文件夹</el-button>
          <el-button
            type="primary"
            plain
            size="mini"
            @click="handEdit"
            v-hasPermi="['system:file:edit']"
          >重命名</el-button>
          <el-button
            type="primary"
            plain
            size="mini"
            @click="handDel"
            v-hasPermi="['system:file:edit']"
          >删除文件</el-button>
        </el-col>
        <!--<el-col :span="1.5">

        </el-col>-->
        <!--<right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>-->
        <!--<el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="" prop="fileName">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入文件名"
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
        </el-form>-->

        <el-col>
          <div class="breadcrumb-wrapper">
            <div class="title">当前位置：</div>
            <el-input
              class="file-path-input"
              ref="filePathInputRef"
              placeholder="请输入路径"
              v-model="inputFilePath"
              size="mini"
              :autofocus="true"
              v-show="isShowInput"
              @blur="handleInputBlurEnter"
              @change="handleInputBlurEnter"
            ></el-input>
            <div
              class="breadcrumb-box"
              :class="{ 'able-input': fileType === 0 }"
              v-show="!isShowInput"
              style="height: 20px"
              @click.self="handleClickBreadCrumbSelf"
            >
              <el-breadcrumb
                v-if="![0, 8].includes(this.fileType) && !['Share'].includes($route.name)"
                separator-class="el-icon-arrow-right" style="line-height: 2;"
              >
                <el-breadcrumb-item>{{ fileTypeMap[fileType] }}</el-breadcrumb-item>
              </el-breadcrumb>
              <el-breadcrumb v-else separator-class="el-icon-arrow-right">
                <el-breadcrumb-item
                  v-for="(item, index) in breadCrumbList"
                  :key="index"
                  :to="{path :item.path}"
                >{{ item.name }}</el-breadcrumb-item
                >
              </el-breadcrumb>
            </div>
          </div>
        </el-col>
        <div></div>
      </el-col>
      <el-col>
        <el-table v-loading="loading" :data="fileList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" v-if="checkRole(['admin'])" width="50" align="center"/>
          <el-table-column label="文件名" align="center" key="name" prop="name" v-if="columns[0].visible"
                           sortable/>
          <el-table-column label="类型" align="center" key="typeName" prop="typeName" v-if="columns[1].visible"
                           sortable
                           :show-overflow-tooltip="true"/>
          <el-table-column label="修改日期" align="center" key="modTime" prop="modTime" v-if="columns[2].visible"
                           sortable
                           :show-overflow-tooltip="true"/>
          <el-table-column label="大小" align="center" key="size" prop="size" v-if="columns[3].visible"
                           sortable
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

    </el-row>


    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
              <el-col :span="12">
                <el-form-item label="文件名" prop="name">
                  <el-input v-model="form.name" placeholder="请输入文件名" maxlength="30" />
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
          <div slot="footer" class="dialog-footer" style="text-align: center">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

    <!--用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls,.exe,.bat,.jpg,.png,.doc,.docx,.txt,.java,.xml,.js,"
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
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
          </el-link>
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
  import {checkPermi, checkRole} from "../../../utils/permission";
  import {getToken} from "../../../utils/auth";
  import {listFile} from "../../../api/system/file.js";

  export default {
    name: "File",
    data() {
      return {
        fileType:'',
        filePath:'',
        //动态面包屑路由集合
        breadCrumbList:[],
        fileTypeMap: {
          1: '全部图片',
          2: '全部文档',
          3: '全部视频',
          4: '全部音乐',
          5: '其他',
          6: '回收站'
        },
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        isShowInput: false, //  是否展示路径输入框
        inputFilePath: '',//  路径输入
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        //控制输入框是否只读
        read: false,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 学生成绩表格
        fileList: null,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //文件路径
        path: '',
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
          headers: {Authorization: "Bearer " + getToken()},
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/system/file/upload"
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: undefined,
          path: undefined,
        },
        // 列信息
        columns: [
          {key: 0, label: `文件名`, visible: true},
          {key: 1, label: `类型`, visible: true},
          {key: 2, label: `修改日期`, visible: true},
          {key: 3, label: `大小`, visible: true}
        ],
      };
    },
    watch: {
      form(val) {
        console.log(val)
      }
    },
    created() {

      this.getList('/');
    },
    methods: {
      //权限控制
      checkRole,
      checkPermi,
      /** 查询用户列表 */
      getList(pathVal) {
        this.loading = true;
        this.queryParams.path = pathVal;
        this.filePath=pathVal;
        this.fileType=0;
        this.getPathArray()
        // this.breadCrumbList=this.getPathArray();
        listFile(this.queryParams).then(response => {
            console.log(response)
            this.fileList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
        this.loading = false;
      },

      getPathArray() {
        let filePathList = this.filePath ? this.filePath.split('/') : []
        console.log(filePathList)
        let res = [] //  返回结果数组
        let _path = [] //  存放祖先路径
        for (let i = 0; i < filePathList.length; i++) {
          if (filePathList[i]) {
            _path.push(filePathList[i] + '/')
            res.push({
              path: _path.join(''),
              name: filePathList[i]
            })
          } else if (i === 0) {
            //  根目录
            filePathList[i] = '/'
            _path.push(filePathList[i])
            res.push({
              path: '/',
              name:
                this.fileType === 0
                  ? '全部文件'
                  : this.fileType === 8
                  ? '我的分享'
                  : this.$route.name === 'Share'
                    ? '全部分享'
                    : ''
            })
          }
        }
        // this.breadCrumbList=res;
      },

      /**
       * 点击面包屑导航栏空白处
       */
      handleClickBreadCrumbSelf() {
        if (this.fileType === 0) {
          this.inputFilePath = this.filePath
          this.isShowInput = true
          this.$nextTick(() => {
            this.$refs.filePathInputRef.focus()
          })
        }
      },
      /**
       * 路径输入框失去焦点或用户按下回车时触发
       */
      handleInputBlurEnter() {
        this.isShowInput = false
        if (this.inputFilePath !== this.filePath) {
          this.getList(this.inputFilePath);
        }
      },



      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          name: undefined,
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        if(this.inputFilePath!==this.filePath){
          this.getList(this.inputFilePath);
        }
        if(this.inputFilePath===this.filePath){
          this.getList(this.filePath)
        }

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
      handProtfolio(){
        this.reset();
        this.open = true;
        this.title = "新增文件夹";
      },
      /** 新增按钮操作 */
      /*handleAdd() {
        this.reset();
        /!*this.getTreeselect();*!/
        this.read=false;
        this.open = true;
        this.title = "添加学生成绩";
      },*/
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
            if (this.form.name != undefined) {
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
      /*handleDelete(row) {
        const delIds = row.id || this.ids;
        this.$modal.confirm('是否确认删除学号为"' + delIds + '"的学生成绩？').then(function() {
          return deleteGrade(delIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },*/
      /** 导出按钮操作 */
      /*handleExport() {
        this.download('system/grade/export', {
          ...this.queryParams
        }, `grade_${new Date().getTime()}.xlsx`)
      },*/
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "文件上传";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        this.download('system/file/download', {
        }, `file_template_${new Date().getTime()}.xlsx`)
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
        this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      }
    }
  }
</script>

<style lang="sass" scoped>

</style>
