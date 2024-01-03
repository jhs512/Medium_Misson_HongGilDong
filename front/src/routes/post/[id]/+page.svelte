<script lang="ts">
  import ToastUiEditor from '$lib/components/ToastUiEditor.svelte';
  import type { components } from '$lib/types/api/v1/schema';

  const { data } = $props<{ data: { post: components['schemas']['PostDto'] } }>();
  const { post } = data;
</script>

<svelte:head>
  <title>글 {post.title}</title>
</svelte:head>

<div>
  <h1>{post.title}</h1>

  {#if post}
    <ToastUiEditor body={post.body} viewer={true} />
  {/if}
</div>
