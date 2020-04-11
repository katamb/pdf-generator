// Duplicate imports have to be allowed here, if they are taken out from `declare module`,
// then the system stops recognizing *.vue and *.svg imports!!!

declare module '*.vue' {
  // eslint-disable-next-line import/no-duplicates
  import Vue from 'vue';

  export default Vue;
}

declare module '*.svg' {
  // eslint-disable-next-line import/no-duplicates
  import Vue, { VueConstructor } from 'vue';

  const content: VueConstructor<Vue>;
  export default content;
}
