import type { Meal, MealCategory } from '../types'

interface MealsTableProps {
	meals: Meal[]
	categories: MealCategory[]
	isAdmin: boolean
	onEdit: (meal: Meal) => void
	onDelete: (id: number) => void
}

const MealsTable = ({ meals, categories, isAdmin, onEdit, onDelete }: MealsTableProps) => {
	const getCategoryName = (categoryId: number) => {
		const category = categories.find((item) => item.id === categoryId)
		return category?.name ?? `#${categoryId}`
	}

	return (
		<div className="table-responsive">
			<table className="table table-striped">
				<thead>
					<tr>
						<th>Nazwa</th>
						<th>Opis</th>
						<th>Cena</th>
						<th>Kategoria</th>
						{isAdmin && <th>Akcje</th>}
					</tr>
				</thead>
				<tbody>
					{meals.map((meal) => (
						<tr key={meal.id}>
							<td>{meal.name}</td>
							<td>{meal.description}</td>
							<td>{Number(meal.price).toFixed(2)} zł</td>
							<td>{getCategoryName(meal.mealCategoryId)}</td>
							{isAdmin && (
								<td className="text-nowrap">
									<button
										className="btn btn-sm btn-outline-primary me-2"
										onClick={() => onEdit(meal)}
									>
										Edytuj
									</button>
									<button
										className="btn btn-sm btn-outline-danger"
										onClick={() => onDelete(meal.id)}
									>
										Usuń
									</button>
								</td>
							)}
						</tr>
					))}
				</tbody>
			</table>
			{!meals.length && <p>Brak posiłków.</p>}
		</div>
	)
}
export default MealsTable
