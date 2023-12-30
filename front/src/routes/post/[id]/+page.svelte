<script lang="ts">
	const { data } = $props();
</script>

<svelte:head>
	<title>글 {data.post.title}</title>
</svelte:head>

<div>
	<h1>{data.post.title}</h1>

	<p>{data.post.body}</p>
</div>
