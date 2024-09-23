<template>
  <div class="home">
    <MdEditor :value="Mdvalue" :handleChange="onMdChange" />
    <CodeEditor :value="Codevalue" :handleChange="onCodeChange" />
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import CodeEditor from "@/components/CodeEditor.vue"; // @ is an alias to /src

const Mdvalue = ref();
const onMdChange = (v: string) => {
  Mdvalue.value = v;
};
const Codevalue = ref();
const onCodeChange = (v: string) => {
  Codevalue.value = v;
};
</script>
