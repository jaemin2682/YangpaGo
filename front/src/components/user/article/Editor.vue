<template>
  <div class="editor">
    <ImageModal ref="ytmodal" @onConfirm="addCommand" />
    <SummaryModal ref="smodal" :isEdit="isEdit" />
    <div class="container-editor">
      <header>
        <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
          <div class="menubar">

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.heading({ level: 1 }) }"
              @click="commands.heading({ level: 1 })"
            >
              H1
            </button>
            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.heading({ level: 2 }) }"
              @click="commands.heading({ level: 2 })"
            >
              H2
            </button>

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.heading({ level: 3 }) }"
              @click="commands.heading({ level: 3 })"
            >
              H3
            </button>
            <div class="vertical-line"></div>

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.bold() }"
              @click="commands.bold"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <title>굵게</title>
                <path
                  d="M17.194,10.962A6.271,6.271,0,0,0,12.844.248H4.3a1.25,1.25,0,0,0,0,2.5H5.313a.25.25,0,0,1,.25.25V21a.25.25,0,0,1-.25.25H4.3a1.25,1.25,0,1,0,0,2.5h9.963a6.742,6.742,0,0,0,2.93-12.786Zm-4.35-8.214a3.762,3.762,0,0,1,0,7.523H8.313a.25.25,0,0,1-.25-.25V3a.25.25,0,0,1,.25-.25Zm1.42,18.5H8.313a.25.25,0,0,1-.25-.25V13.021a.25.25,0,0,1,.25-.25h4.531c.017,0,.033,0,.049,0l.013,0h1.358a4.239,4.239,0,0,1,0,8.477Z"
                />
              </svg>
            </button>

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.italic() }"
              @click="commands.italic"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <title>기울이기</title>
                <path
                  d="M22.5.248H14.863a1.25,1.25,0,0,0,0,2.5h1.086a.25.25,0,0,1,.211.384L4.78,21.017a.5.5,0,0,1-.422.231H1.5a1.25,1.25,0,0,0,0,2.5H9.137a1.25,1.25,0,0,0,0-2.5H8.051a.25.25,0,0,1-.211-.384L19.22,2.98a.5.5,0,0,1,.422-.232H22.5a1.25,1.25,0,0,0,0-2.5Z"
                />
              </svg>
            </button>

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.strike() }"
              @click="commands.strike"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <title>취소선</title>
                <path
                  d="M23.75,12.952A1.25,1.25,0,0,0,22.5,11.7H13.564a.492.492,0,0,1-.282-.09c-.722-.513-1.482-.981-2.218-1.432-2.8-1.715-4.5-2.9-4.5-4.863,0-2.235,2.207-2.569,3.523-2.569a4.54,4.54,0,0,1,3.081.764A2.662,2.662,0,0,1,13.615,5.5l0,.3a1.25,1.25,0,1,0,2.5,0l0-.268A4.887,4.887,0,0,0,14.95,1.755C13.949.741,12.359.248,10.091.248c-3.658,0-6.023,1.989-6.023,5.069,0,2.773,1.892,4.512,4,5.927a.25.25,0,0,1-.139.458H1.5a1.25,1.25,0,0,0,0,2.5H12.477a.251.251,0,0,1,.159.058,4.339,4.339,0,0,1,1.932,3.466c0,3.268-3.426,3.522-4.477,3.522-1.814,0-3.139-.405-3.834-1.173a3.394,3.394,0,0,1-.65-2.7,1.25,1.25,0,0,0-2.488-.246A5.76,5.76,0,0,0,4.4,21.753c1.2,1.324,3.114,2,5.688,2,4.174,0,6.977-2.42,6.977-6.022a6.059,6.059,0,0,0-.849-3.147.25.25,0,0,1,.216-.377H22.5A1.25,1.25,0,0,0,23.75,12.952Z"
                />
              </svg>
            </button>

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.underline() }"
              @click="commands.underline"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <title>밑줄</title>
                <path
                  d="M22.5,21.248H1.5a1.25,1.25,0,0,0,0,2.5h21a1.25,1.25,0,0,0,0-2.5Z"
                />
                <path
                  d="M1.978,2.748H3.341a.25.25,0,0,1,.25.25v8.523a8.409,8.409,0,0,0,16.818,0V3a.25.25,0,0,1,.25-.25h1.363a1.25,1.25,0,0,0,0-2.5H16.3a1.25,1.25,0,0,0,0,2.5h1.363a.25.25,0,0,1,.25.25v8.523a5.909,5.909,0,0,1-11.818,0V3a.25.25,0,0,1,.25-.25H7.7a1.25,1.25,0,1,0,0-2.5H1.978a1.25,1.25,0,0,0,0,2.5Z"
                />
              </svg>
            </button>

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.paragraph() }"
              @click="commands.paragraph"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <title>문단</title>
                <path
                  d="M22.5.248H7.228a6.977,6.977,0,1,0,0,13.954H9.546a.25.25,0,0,1,.25.25V22.5a1.25,1.25,0,0,0,2.5,0V3a.25.25,0,0,1,.25-.25h3.682a.25.25,0,0,1,.25.25V22.5a1.25,1.25,0,0,0,2.5,0V3a.249.249,0,0,1,.25-.25H22.5a1.25,1.25,0,0,0,0-2.5ZM9.8,11.452a.25.25,0,0,1-.25.25H7.228a4.477,4.477,0,1,1,0-8.954H9.546A.25.25,0,0,1,9.8,3Z"
                />
              </svg>
            </button>
            <div class="vertical-line"></div>

            <button
              class="menubar__button"
              :class="{ 'is-active': isActive.blockquote() }"
              @click="commands.blockquote"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <title>인용문</title>
                <path
                  d="M18.559,3.932a4.942,4.942,0,1,0,0,9.883,4.609,4.609,0,0,0,1.115-.141.25.25,0,0,1,.276.368,6.83,6.83,0,0,1-5.878,3.523,1.25,1.25,0,0,0,0,2.5,9.71,9.71,0,0,0,9.428-9.95V8.873A4.947,4.947,0,0,0,18.559,3.932Z"
                />
                <path
                  d="M6.236,3.932a4.942,4.942,0,0,0,0,9.883,4.6,4.6,0,0,0,1.115-.141.25.25,0,0,1,.277.368A6.83,6.83,0,0,1,1.75,17.565a1.25,1.25,0,0,0,0,2.5,9.711,9.711,0,0,0,9.428-9.95V8.873A4.947,4.947,0,0,0,6.236,3.932Z"
                />
              </svg>
            </button>

            <button class="menubar__button" @click="commands.horizontal_rule">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
              >
                <path
                  d="M5,13 C4.44771525,13 4,12.5522847 4,12 C4,11.4477153 4.44771525,11 5,11 L19,11 C19.5522847,11 20,11.4477153 20,12 C20,12.5522847 19.5522847,13 19,13 L5,13 Z"
                />
              </svg>
            </button>

            <button
              class="menubar__button"
              @click="openImgModal(commands.image)"
            >
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <title>이미지 첨부</title>
                <circle cx="9.75" cy="6.247" r="2.25" />
                <path
                  d="M16.916,8.71A1.027,1.027,0,0,0,16,8.158a1.007,1.007,0,0,0-.892.586L13.55,12.178a.249.249,0,0,1-.422.053l-.82-1.024a1,1,0,0,0-.813-.376,1.007,1.007,0,0,0-.787.426L7.59,15.71A.5.5,0,0,0,8,16.5H20a.5.5,0,0,0,.425-.237.5.5,0,0,0,.022-.486Z"
                />
                <path
                  d="M22,0H5.5a2,2,0,0,0-2,2V18.5a2,2,0,0,0,2,2H22a2,2,0,0,0,2-2V2A2,2,0,0,0,22,0Zm-.145,18.354a.5.5,0,0,1-.354.146H6a.5.5,0,0,1-.5-.5V2.5A.5.5,0,0,1,6,2H21.5a.5.5,0,0,1,.5.5V18A.5.5,0,0,1,21.855,18.351Z"
                />
                <path
                  d="M19.5,22H2.5a.5.5,0,0,1-.5-.5V4.5a1,1,0,0,0-2,0V22a2,2,0,0,0,2,2H19.5a1,1,0,0,0,0-2Z"
                />
              </svg>
            </button>
          </div>
        </editor-menu-bar>
        <!-- <div class="end">
          <button class="btn" @click="openSummaryModal">
            <span v-if="!isEdit">작성</span>
            <span v-else>수정</span> 완료
          </button>
        </div> -->
      </header>
      <main>
        <div class="editor--left">
          <editor-content
            ref="editorContent"
            class="editor__content"
            :editor="editor"
          />
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import { Editor, EditorContent, EditorMenuBar} from "tiptap";
import javascript from "highlight.js/lib/languages/javascript";
import css from "highlight.js/lib/languages/css";
import java from "highlight.js/lib/languages/java";
import json from "highlight.js/lib/languages/json";
import markdown from "highlight.js/lib/languages/markdown";
import python from "highlight.js/lib/languages/python";
import ruby from "highlight.js/lib/languages/ruby";
import swift from "highlight.js/lib/languages/swift";
import cpp from "highlight.js/lib/languages/cpp";
import cs from "highlight.js/lib/languages/cs";

import ImageModal from "./ImageModal";
import SummaryModal from "./SummaryModal";
import { mapGetters } from "vuex";
import axios from "axios";

import {
  CodeBlockHighlight,
  Blockquote,
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  ListItem,
  TodoItem,
  TodoList,
  Bold,
  Italic,
  Link,
  Strike,
  Underline,
  History,
  Image,
  Placeholder
} from "tiptap-extensions";
export default {
  components: {
    EditorContent,
    EditorMenuBar,
    ImageModal,
    SummaryModal,
  },
  watch: {
    title: function(newTitle) {
      this.previewData.title = newTitle;
    },
    tagList: function(newTagList) {
      this.previewData.tagList = newTagList;
    }
  },
  data() {
    return {
      isEdit: false,
      title: "",
      html: "",
      isUpdated: false,
      picture: "",
      summary: "",
      editor: new Editor({
        autoFocus: true,
        extensions: [
          new CodeBlockHighlight({
            languages: {
              javascript,
              css,
              java,
              json,
              markdown,
              python,
              ruby,
              swift,
              cpp,
              cs
            }
          }),
          new Blockquote(),
          new CodeBlock(),
          new HardBreak(),
          new Heading({ levels: [1, 2, 3] }),
          new HorizontalRule(),
          new ListItem(),
          new TodoItem(),
          new TodoList(),
          new Link(),
          new Bold(),
          new Italic(),
          new Strike(),
          new Underline(),
          new History(),
          new Image(),
          new Placeholder({
            emptyEditorClass: "is-editor-empty",
            emptyNodeClass: "is-empty",
            emptyNodeText: "내용을 입력하세요",
            showOnlyWhenEditable: true,
            showOnlyCurrent: true
          })
        ],
        onUpdate: ({ getHTML }) => {
          this.isUpdated = true;
          this.html = getHTML();
          setTimeout(() => {
            const content = this.$refs.editorContent.editor.view.dom.innerHTML;
            this.$refs.preview.updatePreviewContent(content);
          }, 100);
        }
      })
    };
  },
  methods: {
    ...mapGetters({
      getUserInfo: "user/getUserInfo"
    }),
    
    openImgModal(command) {
      this.$refs.ytmodal.showModal(command);
    },
    openSummaryModal() {
      if (!this.isUpdated) {
        this.html = this.$refs.editorContent.editor.view.dom.innerHTML;
      }

      const articleData = {
        title: this.title,
        content: this.html,
        writer: this.getUserInfo().email,
        summary: this.summary,
        picture: this.picture,
        id: this.$route.params.targetId,
        good: this.like,
        views: this.views
      };
      console.log(articleData);
      this.$refs.smodal.showModal(articleData);
    },
    addCommand(data) {
      if (data.command !== null) {
        data.data.forEach(elem => {
          data.command(elem);
        });
      }
    },
    async initEditorData() {
      // 기존 글 수정
      if (Object.keys(this.$route.params).length === 1) {
        this.isEdit = true;
        this.getEditData();
      }
      // 새 글
      else {
        const res = await axios.get(
          `${this.$apiServer}/temppost/is?email=${this.getUserInfo().email}`
        );

        // 임시저장 있을 때
        if (res.data) {
          if (
            confirm(
              "임시 저장된 글을 불러오시겠습니까? (취소할 경우 임시 저장된 글은 삭제됩니다.)"
            )
          ) {
            try {
              const { data } = await axios.get(
                `${this.$apiServer}/temppost?email=${this.getUserInfo().email}`
              );
              this.title = data.title;
              this.content = data.content;
              this.editor.setContent(this.content);
              this.tagSetting(data.tag);
              this.picture = data.picture;
              this.summary = data.summary;
            } catch (error) {
              console.log(error);
            }
          } else {
            try {
              axios.delete(
                `${this.$apiServer}/temppost?email=${this.getUserInfo().email}`
              );
            } catch (error) {
              console.log(error);
            }
          }
        }
        this.isEdit = false;
      }
    }
  },
  beforeDestroy() {
    this.editor.destroy();
  },

  async created() {
    this.initEditorData();
  }
};
</script>

<style lang="scss" scope>
@import "@/assets/sass/main.scss";

.editor {
  text-align: left;
}

input {
  caret-color: #999999;
}

.title {
  width: 100%;
  border: none;
  font-size: 2.5em;
  font-weight: 700;
  border-bottom: 1px solid #dfdfdf;
  padding: 10px 0px;
  margin-bottom: 16px;
  background-color: transparent;
  &::placeholder {
    color: #c4c4c4;
  }
}

.container-tags {
  display: flex;
  flex-wrap: wrap;

  width: 100%;
  div {
    display: flex;
    align-items: center;
    height: 1.8em;
    background-color: rgba(0, 0, 0, 0);
    border: 2px solid #1a7cff;
    color: #1a7cff;
    border-radius: 15px;
    padding: 0px 10px;
    padding-bottom: 2px;
    margin-right: 10px;
    margin-bottom: 4px;

    button {
      display: flex;
      align-items: center;
      justify-content: center;
      margin-left: 9px;
      margin-top: 2px;
    }
    svg {
      width: 16px;
      height: 16px;
      fill: #c1d8ff;
    }
  }
}

.input-tags {
  width: 100%;
  height: 2em;
  border-radius: 5px;
  margin-bottom: 20px;
  border: none;
  background-color: transparent;
  font-size: 1.2em;
  color: #1a7cff;
  &::placeholder {
    color: #c4c4c4;
  }
}

// icon design

.icon {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0 0.3rem;
  top: -0.05rem;
  fill: currentColor;

  // &.has-align-fix {
  // 	top: -.1rem;
  // }

  &__svg {
    display: inline-block;
    vertical-align: top;
    width: 100%;
    height: 100%;
  }

  &:first-child {
    margin-left: 0;
  }

  &:last-child {
    margin-right: 0;
  }
}

// svg sprite
body > svg,
.icon use > svg,
symbol {
  path,
  rect,
  circle,
  g {
    fill: currentColor;
    stroke: none;
  }

  *[d="M0 0h24v24H0z"] {
    display: none;
  }
}
// code highlight
pre {
  &::before {
    content: attr(data-language);
    text-transform: uppercase;
    display: block;
    text-align: right;
    font-weight: bold;
    font-size: 0.6rem;
  }

  code {
    .hljs-comment,
    .hljs-quote {
      color: #999999;
    }

    .hljs-variable,
    .hljs-template-variable,
    .hljs-attribute,
    .hljs-tag,
    .hljs-name,
    .hljs-regexp,
    .hljs-link,
    .hljs-name,
    .hljs-selector-id,
    .hljs-selector-class {
      color: #f2777a;
    }

    .hljs-number,
    .hljs-meta,
    .hljs-built_in,
    .hljs-builtin-name,
    .hljs-literal,
    .hljs-type,
    .hljs-params {
      color: #f99157;
    }

    .hljs-string,
    .hljs-symbol,
    .hljs-bullet {
      color: #99cc99;
    }

    .hljs-title,
    .hljs-section {
      color: #ffcc66;
    }

    .hljs-keyword,
    .hljs-selector-tag {
      color: #6699cc;
    }

    .hljs-emphasis {
      font-style: italic;
    }

    .hljs-strong {
      font-weight: 700;
    }
  }
}
</style>
