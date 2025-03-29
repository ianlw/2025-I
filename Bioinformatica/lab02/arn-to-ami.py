from Bio import SeqIO
from Bio.Seq import Seq

from Bio import SeqIO

fasta = "secuencia_ARN.fasta"

for _ in SeqIO.parse(fasta, "fasta"):
    print(f"ID: {_.id}")
    print(f"Secuencia ADN: {_.seq}")
    arn = _.seq
    aminoacidos = str(Seq(arn).translate(to_stop=True))
    print('Secuencia de Aminoácidos:')
    print(aminoacidos)


