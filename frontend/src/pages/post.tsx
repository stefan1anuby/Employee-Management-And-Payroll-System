import { Helmet } from 'react-helmet-async';

import { CONFIG } from 'src/config-global';

import { CreatePost } from 'src/sections/post/view';

// ----------------------------------------------------------------------

export default function Page() {
  return (
    <>
      <Helmet>
        <title> {`Create Post - ${CONFIG.appName}`}</title>
        
      </Helmet>

      <CreatePost />
    </>
  );
}
