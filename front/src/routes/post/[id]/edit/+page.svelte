<script lang="ts">
	import { page } from '$app/stores';
	import ToastUiEditor from '$lib/components/ToastUiEditor.svelte';
	import rq from '$lib/rq/rq.svelte';
	import type { components } from '$lib/types/api/v1/schema';

	let post: components['schemas']['PostDto'] | null = $state(null);

	rq.effect(async () => {
		const { data, error } = await rq
			.apiEndPoints()
			.GET('/api/v1/posts/{id}', { params: { path: { id: parseInt($page.params.id) } } });

		post = data!.data.item;
	});
</script>

{#if post}
	<ToastUiEditor body={post.body} />
{/if}
