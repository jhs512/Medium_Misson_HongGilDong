

export const index = 2;
let component_cache;
export const component = async () => component_cache ??= (await import('../entries/pages/_page.svelte.js')).default;
export const imports = ["_app/immutable/nodes/2.p657pMTT.js","_app/immutable/chunks/disclose-version.TT3aB54g.js","_app/immutable/chunks/runtime.GVHMQo70.js","_app/immutable/chunks/render.kzWBI0oE.js"];
export const stylesheets = [];
export const fonts = [];
