import type { Config } from 'jest'

const config: Config = {
  moduleFileExtensions: ['js', 'json', 'ts'],
  rootDir: 'src',
  testRegex: '.*\\.test\\.ts$',
  transform: { '^.+\\.ts$': 'ts-jest' },
  collectCoverageFrom: ['**/*.service.ts', '**/*.guard.ts', '**/*.filter.ts'],
  coverageDirectory: '../coverage',
  testEnvironment: 'node',
}

export default config
