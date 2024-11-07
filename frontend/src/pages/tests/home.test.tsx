// eslint-disable-next-line import/no-extraneous-dependencies
import React from 'react';
// eslint-disable-next-line import/no-extraneous-dependencies
import '@testing-library/jest-dom';
import { HelmetProvider } from 'react-helmet-async';
// eslint-disable-next-line import/no-extraneous-dependencies
import { render , waitFor} from '@testing-library/react';

import Page from '../home';

test('test expected title home page', async () => {
  render(
      <HelmetProvider>
          <Page />
      </HelmetProvider>
  );

  const title = `Dashboard - Minimal UI`;

  
  await waitFor(() => {
      expect(document.title).toBe(title);
  });
});

test('test expected salaries graph',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const divElement = document.querySelector("div[name='graph-salaries']");
    expect(divElement).toBeInTheDocument();
    
  });
  test('test expected gender graph',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const divElement = document.querySelector("div[name='graph-gender']");
    expect(divElement).toBeInTheDocument();
    
    
  });
  test('test expected age graph',  () => {
    render(
        <HelmetProvider>
            <Page />
        </HelmetProvider>
    );
  
    const divElement = document.querySelector("div[name='graph-age']");
    expect(divElement).toBeInTheDocument();
    
  });




