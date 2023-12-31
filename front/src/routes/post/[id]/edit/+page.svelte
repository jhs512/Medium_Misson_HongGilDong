<script lang="ts">
  import { page } from '$app/stores';
  import ToastUiEditor from '$lib/components/ToastUiEditor.svelte';
  import rq from '$lib/rq/rq.svelte';
  import type { components } from '$lib/types/api/v1/schema';
  import hotkeys from 'hotkeys-js';

  let post: components['schemas']['PostDto'] | null = $state(null);

  let oldBody: string = '';

  let toastUiEditor: any | undefined = $state();

  function saveToLocalStorage(id: number, body: string) {
    const key = 'posts_' + id;
    // LocalStorage에서 기존 데이터를 가져옵니다.
    const existingData = localStorage.getItem(key);

    // 기존 데이터가 있으면 JSON으로 파싱하고, 없으면 빈 배열을 사용합니다.
    const posts = existingData ? JSON.parse(existingData) : [];

    // 새 데이터를 배열에 추가합니다.
    posts.push({
      id,
      body: body,
      date: new Date().toISOString()
    });

    // 배열의 크기가 10을 초과하면 가장 오래된 항목(첫 번째 항목)을 제거합니다.
    if (posts.length > 10) {
      posts.shift(); // 배열의 첫 번째 항목을 제거합니다.
    }

    // 변경된 배열을 JSON 문자열로 변환하여 LocalStorage에 저장합니다.
    localStorage.setItem(key, JSON.stringify(posts));
  }

  const Post__save = async () => {
    const newBody = toastUiEditor.editor.getMarkdown().trim();

    if (oldBody === newBody) {
      return;
    }

    const { data, error } = await rq.apiEndPoints().PUT('/api/v1/posts/{id}', {
      params: { path: { id: parseInt($page.params.id) } },
      body: { body: newBody }
    });

    oldBody = newBody;

    saveToLocalStorage(parseInt($page.params.id), newBody);

    if (data) {
      rq.msgInfo(data.msg);
    }
  };

  $effect(() => {
    hotkeys.filter = function (event) {
      return true;
    };

    hotkeys('ctrl+s,cmd+s', 'postEdit', function (event, handler) {
      Post__save();

      event.preventDefault();
    });

    hotkeys('ctrl+q,cmd+q', 'postEdit', function (event, handler) {
      toastUiEditor.switchTab();
      event.preventDefault();
    });

    hotkeys.setScope('postEdit');

    return () => {
      hotkeys.deleteScope('postEdit');
    };
  });

  rq.effect(async () => {
    const { data, error } = await rq
      .apiEndPoints()
      .GET('/api/v1/posts/{id}', { params: { path: { id: parseInt($page.params.id) } } });

    post = data!.data.item;
    oldBody = post.body;
  });
</script>

{#if post}
  <ToastUiEditor bind:this={toastUiEditor} body={post.body} />
{/if}
