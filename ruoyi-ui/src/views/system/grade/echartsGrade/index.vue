<template>
  <div>
    <template>
      <div style="padding-left:150px;margin-top: 20px">
        <label>科目</label>
        <el-select v-model="value" placeholder="请选择">
          <el-option
            v-for="item in project"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            <span style="float: left">{{ item.label }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
          </el-option>
        </el-select>
      </div>
    </template>
    <div id="myChart" :style="{width: '100%', height: '500px'}"></div>
  </div>
</template>

<script>
  import {getSysGradeByProject} from "../../../../api/system/grade";

  export default {
    name: "EchartsGrade",
    data() {
      return {
        project: [{
          value: 'chinese',
          label: '语文'
        }, {
          value: 'english',
          label: '英语'
        }, {
          value: 'mathematics',
          label: '数学'
        }, {
          value: 'history',
          label: '历史'
        }, {
          value: 'biology',
          label: '生物'
        }, {
          value: 'chemistry',
          label: '化学'
        }, {
          value: 'physics',
          label: '物理'
        }, {
          value: 'geography',
          label: '地理'
        }, {
          value: 'politics',
          label: '政治'
        }],
        value: '',
        fieldName:'',
        names:[],
        fieldValues:[],
      }
    },
    //监视select中的value值改变
    watch: {
      value(val) {
        this.chart(val)
      }
    },
    mounted() {
      this.value=this.project[0].value
      this.chart(this.value)
      this.drawLine();
    },
    methods: {
      chart(value){
        //获取select中的label得值
        let obj = {};
        obj = this.project.find((item)=>{//遍历list的数据
          return item.value === this.value;//筛选出匹配数据
        });
        console.log(obj.label);
        this.fieldName=obj.label;
        this.getEchartsData(this.value)
      },
      getEchartsData(project) {
        getSysGradeByProject(this.value).then(response => {
          console.log()
          console.log(response.rows)
          response.rows.forEach((value,index)=>{
            console.log(value)
            console.log(value[project])
            console.log(index)
            this.names[index]=value['name'];
            this.fieldValues[index]=value[project];
            console.log(this.names)
            console.log(this.fieldValues)
            console.log(this.fieldName)
          })
          this.drawLine()
        })
      },
      drawLine() {
        // 基于准备好的dom，初始化echarts实例
        let myChart = this.echarts.init(document.getElementById('myChart'))
        // 绘制图表
        myChart.setOption({
          tooltip: {},
          xAxis: {
            data: this.names,
            axisLabel :{
              //解决图标变窄时数据显示不全问题
              interval:0,
            },
          },
          yAxis: {},
          legend: {
            //数据为数组
            data: [this.fieldName+'柱',this.fieldName+'折'],
            show:true,
            bottom:0,
            left:'center',
            textStyle: {
              color: "#1fc3ce",
              fontSize: 14
            },
          },
          series: [{
            name: this.fieldName+'柱',
            type: 'bar',
            data: this.fieldValues,
            //柱状图显示值
            itemStyle:{
              normal:{
                color: "#217ec6",
                label:{
                  show:true,
                  position:'top',
                  textStyle:{
                    color:'black',
                    fontSize:16
                  }
                }
              }
            }
          }, {
            name: this.fieldName+'折',
            type: 'line',
            data: this.fieldValues,
            //显示最大值与最小值
            markPoint:{
              data:[
                {type:'min',name:'最小值'},
                {type:'max',name:'最大值'},
              ]
            },
            //显示平均值
            markLine:{
              data:[
                {type:'average',name:'平均值'}
              ]
            },
            itemStyle: {
              normal:{
                color:"#0b3bd2"
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
      }
    }
  }
</script>

<style scoped>

</style>
