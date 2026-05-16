export interface MockResult {
  data: unknown
  error: unknown
  count?: number | null
}

export class MockQueryBuilder {
  private _results: MockResult[] = []

  public readonly calls: { method: string; args: unknown[] }[] = []

  select: jest.Mock = jest.fn(() => this)
  eq: jest.Mock = jest.fn(() => this)
  single: jest.Mock = jest.fn(() => this)
  maybeSingle: jest.Mock = jest.fn(() => this)
  insert: jest.Mock = jest.fn(() => this)
  update: jest.Mock = jest.fn(() => this)
  delete: jest.Mock = jest.fn(() => this)
  order: jest.Mock = jest.fn(() => this)
  range: jest.Mock = jest.fn(() => this)
  limit: jest.Mock = jest.fn(() => this)
  or: jest.Mock = jest.fn(() => this)
  ilike: jest.Mock = jest.fn(() => this)
  gte: jest.Mock = jest.fn(() => this)
  lte: jest.Mock = jest.fn(() => this)

  setResult(data: unknown, error: unknown = null) {
    const count = Array.isArray(data) ? data.length : undefined
    this._results.push({ data, error, count })
  }

  setError(error: unknown) {
    this._results.push({ data: null, error, count: null })
  }

  then(resolve: (value: unknown) => unknown, _reject?: (reason: unknown) => unknown) {
    const result = this._results.shift() || { data: null, error: null, count: null }
    return Promise.resolve(result).then(resolve)
  }

  catch(reject: (reason: unknown) => unknown) {
    const result = this._results.shift() || { data: null, error: null, count: null }
    return Promise.resolve(result).catch(reject)
  }

  finally(cb: () => void) {
    const result = this._results.shift() || { data: null, error: null, count: null }
    return Promise.resolve(result).finally(cb)
  }
}

export function createMockSupabase() {
  const queryBuilder = new MockQueryBuilder()
  const anonQueryBuilder = new MockQueryBuilder()

  const supabase = {
    from: jest.fn(() => queryBuilder),
    auth: {
      getUser: jest.fn(),
      signInWithPassword: jest.fn(),
      signUp: jest.fn(),
    },
  }

  const anonClient = {
    from: jest.fn(() => anonQueryBuilder),
    auth: {
      getUser: jest.fn(),
      signInWithPassword: jest.fn(),
      signUp: jest.fn(),
    },
  }

  return { supabase, anonClient, queryBuilder, anonQueryBuilder }
}

export type MockSupabase = ReturnType<typeof createMockSupabase>
