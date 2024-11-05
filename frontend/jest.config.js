export default {
    preset: 'ts-jest', // or 'babel-jest' if using Babel
    transform: {
      '^.+\\.(ts|tsx)$': 'ts-jest', // For TypeScript files
      '^.+\\.(js|jsx)$': 'babel-jest', // For JavaScript files
    },
    testEnvironment: 'jest-environment-jsdom', // This is important for testing React components
    // Other Jest configurations...
    moduleFileExtensions: ['js', 'jsx', 'ts', 'tsx', 'json', 'node'],
    moduleNameMapper: {
        '^src/(.*)$': '<rootDir>/src/$1', // Add this line
      }
  };
  