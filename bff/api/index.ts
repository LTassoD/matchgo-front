import { NestFactory } from '@nestjs/core'
import { ExpressAdapter } from '@nestjs/platform-express'
import { ValidationPipe } from '@nestjs/common'
import express from 'express'
import { AppModule } from '../src/app.module'

const server = express()

async function bootstrap() {
  const app = await NestFactory.create(AppModule, new ExpressAdapter(server))

  app.enableCors({
    origin: process.env.NEXT_PUBLIC_APP_URL || '*',
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'OPTIONS'],
    allowedHeaders: ['Content-Type', 'Authorization'],
    credentials: true,
  })

  app.useGlobalPipes(new ValidationPipe({ whitelist: true, forbidNonWhitelisted: true, transform: true }))

  app.setGlobalPrefix('api/v1')

  await app.init()
  return server
}

let cachedApp: any

export default async function handler(req: any, res: any) {
  if (!cachedApp) {
    cachedApp = await bootstrap()
  }
  cachedApp(req, res)
}
