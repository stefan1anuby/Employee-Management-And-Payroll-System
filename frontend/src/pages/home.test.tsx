// eslint-disable-next-line import/no-extraneous-dependencies
import React from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import '@testing-library/jest-dom';
// eslint-disable-next-line import/no-extraneous-dependencies
import { render } from '@testing-library/react';
import { HelmetProvider } from 'react-helmet-async'; // Import HelmetProvider

import { CONFIG } from "src/config-global";

import Page from './home';

beforeAll(() => {
    document.title = ''; // Reset title before tests
  });

  afterAll(() => {
    document.title = ''; // Reset title before tests
  });

describe('Page Component', () => {
  it('renders the correct title', () => {
    // Wrap your Page component in HelmetProvider
    render(
      <HelmetProvider>
        <Page />
      </HelmetProvider>
    );

  //  console.log(document.title);
    
    // Check if the title includes the configured app name
    const title = `Dashboard - ${CONFIG.appName}`;
    expect(document.title).toBeInTheDocument();
  });
});


