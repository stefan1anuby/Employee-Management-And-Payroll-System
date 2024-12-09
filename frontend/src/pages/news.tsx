import { Helmet } from 'react-helmet-async';

import { CONFIG } from 'src/config-global';

import { NewsFeed } from 'src/sections/feed/view';

// ----------------------------------------------------------------------

export default function Page() {
  return (
    <>
      <Helmet>
        <title> {`News Feed - ${CONFIG.appName}`}</title>
        
      </Helmet>

      <NewsFeed />
    </>
  );
}
