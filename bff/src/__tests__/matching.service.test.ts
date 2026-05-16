import { Test, TestingModule } from '@nestjs/testing'
import { MatchingService } from '../modules/matching/matching.service'
import { createMockSupabase, MockSupabase } from './mocks/supabase'

jest.mock('../common/supabase', () => ({
  getServerClient: jest.fn(),
  getAnonClient: jest.fn(),
}))

describe('MatchingService', () => {
  let service: MatchingService
  let mock: MockSupabase
  let originalFetch: typeof global.fetch

  beforeEach(async () => {
    mock = createMockSupabase()
    jest.requireMock('../common/supabase').getServerClient.mockReturnValue(mock.supabase)
    jest.requireMock('../common/supabase').getAnonClient.mockReturnValue(mock.anonClient)
    originalFetch = global.fetch
    global.fetch = jest.fn()

    const module: TestingModule = await Test.createTestingModule({
      providers: [MatchingService],
    }).compile()

    service = module.get<MatchingService>(MatchingService)
  })

  afterEach(() => {
    global.fetch = originalFetch
  })

  describe('runMatching', () => {
    const ofertaId = 'off-1'

    it('calls the edge function and returns result', async () => {
      const expected = { matches: [{ id: 'trab-1', score: 85 }] };
      (global.fetch as jest.Mock).mockResolvedValueOnce({
        ok: true,
        json: () => Promise.resolve(expected),
      })

      const result = await service.runMatching(ofertaId)

      expect(result).toEqual(expected)
      expect(global.fetch).toHaveBeenCalledWith(
        expect.stringContaining('/functions/v1/matching/run?oferta_id=off-1'),
        expect.objectContaining({ headers: expect.objectContaining({ Authorization: expect.stringContaining('Bearer ') }) }),
      )
    })

    it('throws when edge function returns error', async () => {
      ;(global.fetch as jest.Mock).mockResolvedValueOnce({ ok: false })

      await expect(service.runMatching(ofertaId)).rejects.toThrow('Error al ejecutar matching')
    })
  })

  describe('getMatches', () => {
    it('returns matches for an offer sorted by score desc', async () => {
      const matches = [
        { id: 'post-1', score_match: 90, trabajador: { id: 'trab-1', nombre_completo: 'Juan' } },
        { id: 'post-2', score_match: 80, trabajador: { id: 'trab-2', nombre_completo: 'María' } },
      ]
      mock.queryBuilder.setResult(matches)

      const result = await service.getMatches('off-1')

      expect(result.data).toHaveLength(2)
      expect(result.total).toBe(2)
      expect(mock.queryBuilder.eq).toHaveBeenCalledWith('oferta_id', 'off-1')
    })

    it('returns empty array when no matches', async () => {
      mock.queryBuilder.setResult([])

      const result = await service.getMatches('off-1')
      expect(result.data).toEqual([])
      expect(result.total).toBe(0)
    })

    it('throws NotFoundException on supabase error', async () => {
      mock.queryBuilder.setError(new Error('DB error'))

      await expect(service.getMatches('off-1')).rejects.toThrow('No se encontraron matches')
    })

    it('respects sortBy and order parameters', async () => {
      mock.queryBuilder.setResult([])

      await service.getMatches('off-1', 'created_at', 'asc')
      expect(mock.queryBuilder.order).toHaveBeenCalledWith('created_at', { ascending: true })
    })
  })
})
