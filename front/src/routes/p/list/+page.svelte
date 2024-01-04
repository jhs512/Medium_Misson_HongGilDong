<script lang="ts">
  import { page } from '$app/stores';
  import Pagination from '$lib/components/Pagination.svelte';
  import rq from '$lib/rq/rq.svelte';

  async function loadPostPage() {
    const kw = $page.url.searchParams.get('kw') ?? '';
    const kwType = $page.url.searchParams.get('kwType') ?? 'ALL';
    const page_ = $page.url.searchParams.get('page') ?? '1';

    const { data, error } = await rq.apiEndPoints().GET('/api/v1/posts', {
      params: {
        query: {
          kw,
          kwType: kwType as any,
          page: parseInt(page_)
        }
      }
    });

    return data!;
  }
</script>

<div class="flex-1 flex justify-center items-center px-3">
  <div class="container w-full">
    <h1>
      <i class="fa-solid fa-list"></i> 글
    </h1>

    {#await loadPostPage()}
      loading...
    {:then { data: { itemPage } }}
      <Pagination page={itemPage} />

      <hr class="my-4" />

      <ul class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-2">
        {#each itemPage.content as post (post.id)}
          <li class="card bg-base-100 shadow-xl">
            <div class="card-body">
              <a class="card-title" href="/p/{post.id}">{post.id}. {post.title}</a>
            </div>
          </li>
        {/each}
      </ul>

      <hr class="my-4" />

      <Pagination page={itemPage} />
    {/await}
  </div>
</div>
