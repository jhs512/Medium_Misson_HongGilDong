<script lang="ts">
  import { page } from '$app/stores';
  import Pagination from '$lib/components/Pagination.svelte';
  import rq from '$lib/rq/rq.svelte';

  async function load() {
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
    <h1 class="mt-4">
      <i class="fa-solid fa-list"></i> 글
    </h1>

    {#await load()}
      loading...
    {:then { data: { itemPage } }}
      <Pagination page={itemPage} />

      <ul class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-4">
        {#each itemPage.content as post (post.id)}
          <li class="card bg-base-100 shadow-xl">
            <div class="card-body">
              <div class="detail grid grid-cols-[repeat(auto-fit,minmax(100px,1fr))] gap-3">
                <a
                  href={`/p/${post.id}`}
                  class="col-span-full text-base-content bg-base-300 rounded p-3 flex items-center justify-center flex-wrap aspect-video"
                >
                  <div class="flex flex-wrap gap-1">
                    <div class="flex gap-1">
                      <span>🗒️</span>
                      <span>{post.title}</span>
                    </div>
                    <div class="flex-grow text-right italic">
                      <span>by</span>
                      <span>{post.authorUsername}</span>
                    </div>
                  </div>
                </a>

                <div class="form-control">
                  <label class="label">
                    <span class="label-text">번호</span>
                  </label>
                  <div>{post.id}</div>
                </div>

                <div class="form-control">
                  <label class="label">
                    <span class="label-text">작성일</span>
                  </label>
                  <div>{post.createDate.substring(2, 16).replace('T', ' ')}</div>
                </div>

                <div class="form-control">
                  <label class="label">
                    <span class="label-text">공개</span>
                  </label>
                  <div>공개</div>
                </div>

                <div class="form-control col-span-full">
                  <label class="label">
                    <span class="label-text">태그</span>
                  </label>
                  <div>
                    <a class="link" href="/post/listByTag/태그1">#태그1</a>
                    <a class="link" href="/post/listByTag/태그2">#태그2</a>
                  </div>
                </div>
              </div>
            </div>
          </li>
        {/each}
      </ul>

      <hr class="my-4" />

      <Pagination page={itemPage} />
    {/await}
  </div>
</div>
