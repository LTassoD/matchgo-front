import { HttpException, HttpStatus } from '@nestjs/common'
import { HttpExceptionFilter } from '../common/filters/http-exception.filter'

describe('HttpExceptionFilter', () => {
  let filter: HttpExceptionFilter
  let mockJson: jest.Mock
  let mockStatus: jest.Mock
  let mockResponse: any

  beforeEach(() => {
    filter = new HttpExceptionFilter()
    mockJson = jest.fn()
    mockStatus = jest.fn().mockReturnValue({ json: mockJson })
    mockResponse = { status: mockStatus }

    jest.useFakeTimers()
    jest.setSystemTime(new Date('2024-01-15T12:00:00.000Z'))
  })

  afterEach(() => {
    jest.useRealTimers()
  })

  it('formats HttpException correctly', () => {
    const exception = new HttpException('Not Found', HttpStatus.NOT_FOUND)
    const host = {
      switchToHttp: () => ({
        getResponse: () => mockResponse,
      }),
    } as any

    filter.catch(exception, host)

    expect(mockStatus).toHaveBeenCalledWith(HttpStatus.NOT_FOUND)
    expect(mockJson).toHaveBeenCalledWith({
      success: false,
      error: 'Not Found',
      statusCode: HttpStatus.NOT_FOUND,
      timestamp: '2024-01-15T12:00:00.000Z',
    })
  })

  it('formats non-HttpException as 500', () => {
    const exception = new Error('Something went wrong')
    const host = {
      switchToHttp: () => ({
        getResponse: () => mockResponse,
      }),
    } as any

    filter.catch(exception, host)

    expect(mockStatus).toHaveBeenCalledWith(HttpStatus.INTERNAL_SERVER_ERROR)
    expect(mockJson).toHaveBeenCalledWith({
      success: false,
      error: 'Error interno del servidor',
      statusCode: HttpStatus.INTERNAL_SERVER_ERROR,
      timestamp: '2024-01-15T12:00:00.000Z',
    })
  })
})
