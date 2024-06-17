<script setup lang="ts">
import { ref, reactive } from 'vue'
import { type AjaxResponse, type Page } from '@/utils/request'
import {
  getInstance,
  getProject,
  type GlInstanceVO,
  type GlProjectBO,
  type GlProjectVO
} from '@/api/gitlab'

const options = ref<{ value: string; label: string; sum: number }[]>([])

getInstance().then((response: AjaxResponse<GlInstanceVO[]>) => {
  if (response.data) {
    options.value = response.data.map((item) => ({
      value: item.host,
      label: item.host,
      sum: item.sum
    }))
  }
})

const form = reactive({
  instance: '',
  name: ''
})

const loading = ref(false)
const tableData = ref<GlProjectVO[]>()
const current = ref()
const size = ref(10)
const total = ref()

const onSubmit = () => {
  loading.value = true
  console.log('submit!', form)

  const params: GlProjectBO = {
    current: current.value,
    size: size.value,
    host: form.instance,
    name: form.name
  }

  getProject(params).then((response: AjaxResponse<Page<GlProjectVO>>) => {
    console.log(response)

    const data = response.data
    total.value = data.total

    tableData.value = data.records.map((item) => ({
      id: item.id,
      host: item.host,
      name: item.name,
      webUrl: item.webUrl
    }))

    loading.value = false
  })
}

const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
  size.value = val
  onSubmit()
}

const handleCurrentChange = (val: number) => {
  console.log(`current page: ${val}`)
  current.value = val
  onSubmit()
}
</script>

<template>
  <el-form v-loading="loading" :inline="true" :model="form" class="demo-form-inline">
    <el-form-item label="GitLab 实例">
      <el-select
        v-model="form.instance"
        clearable
        placeholder="选择 GitLab 实例"
        style="width: 200px"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-form-item>
    <el-form-item label="名称">
      <el-input v-model="form.name" placeholder="搜索名称" clearable />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">搜索</el-button>
    </el-form-item>
  </el-form>

  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="id" width="100" />
    <el-table-column prop="host" label="host" width="200" />
    <el-table-column label="name">
      <template #default="scope">
        <a target="_blank" :href="scope.row.webUrl">{{ scope.row.name }}</a>
      </template>
    </el-table-column>
  </el-table>
  <div v-if="total > 0">
    <el-pagination
      layout="total, sizes, prev, pager, next, jumper"
      v-model:page-size="size"
      :page-sizes="[10, 20, 50, 100]"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<style scoped></style>
