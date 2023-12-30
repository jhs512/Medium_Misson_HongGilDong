import { r as rq } from "../../../../chunks/rq.svelte.js";
const load = async ({ params }) => {
  const { data, error } = await rq.apiEndPoints().GET(`/api/v1/posts/{id}`, {
    params: {
      path: {
        id: parseInt(params.id)
      }
    }
  });
  return {
    post: data.data.item
  };
};
export {
  load
};
