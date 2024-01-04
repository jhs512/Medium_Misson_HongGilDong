<script lang="ts">
  import { page } from '$app/stores';
  import rq from '$lib/rq/rq.svelte';
  import type { components } from '$lib/types/api/v1/schema';
  import Pagination from '$lib/components/Pagination.svelte';

  let postPage: components['schemas']['PageDtoPostListItemDto'] | null = $state(null);

  rq.effect(async () => {
    const kw = $page.url.searchParams.get('kw') ?? '';
    const kwType = $page.url.searchParams.get('kwType') ?? 'ALL';
    const page_ = $page.url.searchParams.get('page') ?? '1';

    const { data } = await rq.apiEndPoints().GET('/api/v1/posts', {
      params: {
        query: {
          kw,
          kwType: kwType as any,
          page: parseInt(page_)
        }
      }
    });

    if (data) {
      postPage = data.data.itemPage;
    }
  });

  function submitSearchForm(this: HTMLFormElement) {
    const form: HTMLFormElement = this;

    form.kw.value = form.kw.value.trim();

    if (form.kw.value.length === 0) {
      rq.msgError('검색어를 입력해주세요.');
      form.kw.focus();
      return;
    }
    const formData = new FormData(form);
    const searchParams = new URLSearchParams();

    for (const [key, value] of formData) {
      searchParams.append(key, value.toString());
    }

    rq.goToCurrentPageWithNewQueryStr(searchParams.toString());
  }
</script>

<div class="flex-1 flex justify-center items-center px-3">
  <div class="container w-full">
    <h1>
      <i class="fa-solid fa-list"></i> 글
    </h1>

    {#if postPage != null}
      <Pagination page={postPage} />

      <hr />

      <ul>
        {#each postPage.content as post}
          <li>
            <a href="/p/{post.id}">{post.id}. {post.title}</a>
          </li>
        {/each}
      </ul>

      <hr />

      <Pagination page={postPage} />
    {/if}
  </div>
</div>

<div>
  <h1>All Posts</h1>

  <form on:submit|preventDefault={submitSearchForm}>
    <select name="kwType" value={$page.url.searchParams.get('kwType') ?? 'TITLE'}>
      <option value="TITLE">제목</option>
      <option value="TITLE_OR_BODY">제목,내용</option>
      <option value="NAME">작성자</option>
      <option value="ALL">전체</option>
    </select>

    <input type="text" name="kw" placeholder="Search..." value={$page.url.searchParams.get('kw')} />
    <button type="submit">Search</button>
  </form>

  <hr />
</div>
