<script lang="ts">
  import { page } from '$app/stores';
  import rq from '$lib/rq/rq.svelte';
  import type { components } from '$lib/types/api/v1/schema';

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

  // 페이지네이션 범위 계산 함수
  function calculatePaginationRange(current: number, total: number, delta = 4) {
    const left = current - delta;
    const right = current + delta;
    const range = [] as { no: number; text: string }[];

    for (let i = 1; i <= total; i++) {
      if (i === 1) {
        range.push({ no: i, text: `${i}` });
      } else if (i == left - 1) {
        range.push({ no: i, text: `...` });
      } else if (i >= left && i <= right) {
        range.push({ no: i, text: `${i}` });
      } else if (i === total) {
        range.push({ no: i, text: `${i}` });
      } else if (i == right + 1) {
        range.push({ no: i, text: `...` });
      }
    }

    return range;
  }

  const pageDelta = 2;
</script>

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

  {#if postPage != null}
    <div>
      <div>현재 페이지 : {postPage.number}</div>
      <div>총 항목 : {postPage.totalElementsCount}개</div>
      <div>현재 페이지 항목 : {postPage.pageElementsCount}개</div>
    </div>

    <hr />

    <div class="flex gap-2">
      {#each calculatePaginationRange(postPage.number, postPage.totalPagesCount, pageDelta) as pageNumber}
        <button
          class={`${pageNumber.no == postPage.number ? 'text-red-500' : ''}`}
          on:click={() => rq.goToCurrentPageWithNewParam('page', `${pageNumber.no}`)}
        >
          {pageNumber.text}
        </button>
      {/each}
    </div>

    <hr />

    <ul>
      {#each postPage.content as post}
        <li>
          <a href="/post/{post.id}">{post.id}. {post.title}</a>
        </li>
      {/each}
    </ul>
  {/if}
</div>
