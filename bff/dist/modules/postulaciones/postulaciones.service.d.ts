export declare class PostulacionesService {
    create(body: any): Promise<any>;
    list(trabajadorId: string, estado?: string): Promise<{
        data: any[];
        total: number;
    }>;
    accept(id: string): Promise<{
        postulacion: any;
        message: string;
    }>;
    reject(id: string, motivo?: string): Promise<{
        postulacion: any;
        message: string;
    }>;
}
