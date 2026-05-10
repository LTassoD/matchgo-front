import { PostulacionesService } from './postulaciones.service';
export declare class PostulacionesController {
    private readonly service;
    constructor(service: PostulacionesService);
    create(body: any): Promise<any>;
    list(trabajadorId: string, estado?: string): Promise<{
        data: any[];
        total: number;
    }>;
    accept(id: string): Promise<{
        postulacion: any;
        message: string;
    }>;
    reject(id: string, body: {
        motivo?: string;
    }): Promise<{
        postulacion: any;
        message: string;
    }>;
}
