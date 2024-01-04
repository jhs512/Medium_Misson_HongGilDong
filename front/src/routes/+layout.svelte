<script lang="ts">
  import rq from '$lib/rq/rq.svelte';
  import '$lib/app.css';
  import { untrack } from 'svelte';

  const { children } = $props();

  rq.effect(async () => {
    untrack(() => {
      rq.initAuth();
    });
  });
</script>

<header class="navbar bg-base-100">
  <div class="navbar-start">
    <div class="dropdown">
      <div tabindex="0" role="button" class="btn btn-ghost btn-circle">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
          ><path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M4 6h16M4 12h16M4 18h7"
          /></svg
        >
      </div>
      <ul
        tabindex="0"
        class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52"
      >
        <li>
          <a href="/p/list"><i class="fa-solid fa-list"></i> 글</a>
        </li>
        {#if rq.isLogout()}
          <li>
            <a href="/member/login"><i class="fa-solid fa-right-to-bracket"></i> 로그인</a>
          </li>
        {/if}
        {#if rq.isLogin()}
          <li>
            <button on:click={() => rq.goToTempPostEditPage()}>
              <i class="fa-solid fa-plus"></i> 글 작성
            </button>
          </li>
          <li>
            <a href="/p/myList"><i class="fa-solid fa-list-check"></i> 내 글</a>
          </li>
          <li>
            <button on:click={() => rq.logout()}>
              <i class="fa-solid fa-right-from-bracket"></i> 로그아웃
            </button>
          </li>
        {/if}
      </ul>
    </div>
  </div>
  <div class="navbar-center">
    <a href="/" class="btn btn-ghost">medium</a>
  </div>
  <div class="navbar-end">
    <button
      class="btn btn-ghost"
      onclick={() => {
        const searchFormModal = (document.querySelector('#searchFormModal') as HTMLDialogElement);
        const searchFormInputSearch = (document.querySelector('#searchFormModal input[type=search]') as HTMLDialogElement);
        
        searchFormModal.showModal();

        searchFormInputSearch.focus();
      }}
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-5 w-5"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
        ><path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
        /></svg
      >
    </button>
    {#if rq.isLogin()}
      <a href="/member/me" class="btn btn-ghost">
        {rq.member.username}
      </a>
    {/if}
  </div>

  <dialog id="searchFormModal" class="modal">
    <div class="modal-box">
      <form method="dialog">
        <button class="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
      </form>

      <form action="/p/list" class="bg-base rounded flex flex-col gap-6">
        <div class="form-control">
          <label class="label">
            <span class="label-text">검색필터</span>
          </label>

          <select name="kwType" class="select select-bordered">
            <option value="ALL">전체</option>
            <option value="TITLE">제목</option>
            <option value="TITLE_OR_BODY">제목,내용</option>
            <option value="NAME">작성자</option>
          </select>
        </div>

        <div class="form-control">
          <label class="label">
            <span class="label-text">검색어</span>
          </label>

          <input placeholder="검색어" class="input input-bordered" name="kw" type="search" />
        </div>

        <div>
          <button class="btn btn-block btn-primary gap-1">
            <i class="fa-solid fa-magnifying-glass"></i>
            <span>검색</span>
          </button>
        </div>
      </form>
    </div>

    <form method="dialog" class="modal-backdrop">
      <button>close</button>
    </form>
  </dialog>
</header>

<main class="flex-1 flex flex-col">
  {@render children()}
</main>
